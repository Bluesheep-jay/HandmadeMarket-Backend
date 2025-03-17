package com.handmadeMarket.VNPay;

import com.handmadeMarket.Order.Order;
import com.handmadeMarket.Order.OrderService;
import com.handmadeMarket.OrderTemp.OrderTemp;
import com.handmadeMarket.Transaction.Transaction;
import com.handmadeMarket.Transaction.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VNPayService {
    private final OrderService orderService;
    private final TransactionService transactionService;

    public VNPayService(OrderService orderService, TransactionService transactionService
    ) {
        this.orderService = orderService;
        this.transactionService = transactionService;
    }

    @Transactional
    public String createOrder(HttpServletRequest request, int amount, List<OrderTemp> tempOrders) {
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang");
        vnp_Params.put("vnp_OrderType", "order-type");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_Returnurl + "?orderIds=" +
                String.join(",", tempOrders.stream().map(OrderTemp::getId).collect(Collectors.toList())));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String salt = VNPayConfig.vnp_HashSecret;
        System.out.println("HashData=" + hashData.toString());
        String vnp_SecureHash = VNPayConfig.hmacSHA512(salt, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
    }


    public int orderReturn(HttpServletRequest request, String orderIds) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII);
            fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
            List<String> orderIdList = Arrays.asList(orderIds.split(","));
            List<Order> orders = orderService.convertTempToRealOrders(orderIdList);

            for (Order order : orders) {
                Transaction transaction = new Transaction();
                transaction.setTransactionNo(fields.get("vnp_TransactionNo").toString() + order.getOrderShopId());
                transaction.setTransactionAmount(BigDecimal.valueOf(order.getTotalPrice()));
                transaction.setCardType(fields.get("vnp_CardType").toString());
                transaction.setTransactionBankCode(fields.get("vnp_BankCode").toString());
                transaction.setTransactionCreatedAt(Instant.now());
                transaction.setOrderId(order.getId());
                transaction.setTransactionUserId(order.getOrderUserId());
                transaction.setTransactionShopId(order.getOrderShopId());

                transactionService.create(transaction);
            }
            return 1;
        } else {
            return 0;
        }

    }

}