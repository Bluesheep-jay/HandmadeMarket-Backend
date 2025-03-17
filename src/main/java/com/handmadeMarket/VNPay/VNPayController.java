package com.handmadeMarket.VNPay;

import com.handmadeMarket.Order.Order;
import com.handmadeMarket.Order.OrderService;
import com.handmadeMarket.OrderTemp.OrderTemp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vnpay")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private OrderService orderService;

    @GetMapping({"", "/"})
    public String home() {
        return "createOrder";
    }

    @PostMapping("/preparePayment")
    public ResponseEntity<?> preparePayment(@RequestBody List<Order> orders, HttpServletRequest request) {
        int totalAmount = orders.stream().mapToInt(Order::getTotalPrice).sum();

        List<OrderTemp> tempOrders = orderService.saveTempOrders(orders);
        String vnpayUrl = vnPayService.createOrder(request, totalAmount, tempOrders);

        return ResponseEntity.ok(Map.of("vnpayUrl", vnpayUrl, "orderIds", tempOrders.stream().map(OrderTemp::getId).collect(Collectors.toList())));
    }

//    @GetMapping("/vnpay-return")
//    public RedirectView handleVNPayReturn(
//            HttpServletRequest request,
//            @RequestParam("vnp_ResponseCode") String responseCode,
//            @RequestParam("orderIds") String orderIds) {
//        String frontendUrl = "http://localhost:3000/customer/vnpay-payment-return";
//
//        int paymentStatus = vnPayService.orderReturn(request, orderIds);
//        if (paymentStatus != 1) {
//            return new RedirectView(frontendUrl + "?paymentStatus=failed");
//        } else {
//            return new RedirectView(frontendUrl + "?paymentStatus=success");
//        }
//    }

    @GetMapping("/vnpay-return")
    public RedirectView handleVNPayReturn(
            HttpServletRequest request,
            @RequestParam("vnp_ResponseCode") String responseCode,
            @RequestParam("orderIds") String orderIds) {

        String frontendUrl = "http://localhost:3000/customer/vnpay-payment-return";
        int paymentStatus = vnPayService.orderReturn(request, orderIds);

        String totalAmount = request.getParameter("vnp_Amount"); // Lấy số tiền giao dịch từ VNPay
        if (paymentStatus != 1) {
            return new RedirectView(frontendUrl + "?paymentStatus=failed&totalAmount=" + totalAmount);
        } else {
            return new RedirectView(frontendUrl + "?paymentStatus=success&totalAmount=" + totalAmount);
        }
    }

}