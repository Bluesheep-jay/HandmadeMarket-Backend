package com.handmadeMarket.Order;

import com.handmadeMarket.DatabaseSequence.SequenceGeneratorService;
import com.handmadeMarket.Order.dto.DailyRevenueResult;
import com.handmadeMarket.Order.dto.MonthlyRevenueResult;
import com.handmadeMarket.Order.dto.OrderWithDetail;
import com.handmadeMarket.Order.dto.OrderWithProduct;
import com.handmadeMarket.OrderTemp.OrderTemp;
import com.handmadeMarket.OrderTemp.OrderTempRepository;
import com.handmadeMarket.Product.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final String COMPLETED_STATUS_ID = "67d8cd2a347ab249ebe8b15b";
    private final String CANCELLED_STATUS_ID = "67dbfb9009072401a615967b";
    private final OrderRepository orderRepository;
    private final OrderTempRepository orderTempRepository;
    private final MongoTemplate mongoTemplate;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ProductService productService;


    public OrderService(OrderRepository orderRepository, OrderTempRepository orderTempRepository,
                        MongoTemplate mongoTemplate, SequenceGeneratorService sequenceGeneratorService,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderTempRepository = orderTempRepository;
        this.mongoTemplate = mongoTemplate;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.productService = productService;
    }

    public long getTotalOrder(){
        return orderRepository.count();
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<OrderWithDetail> getAllOrdersWithDetail() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.addFields()
                        .addField("order_user_obj_id").withValue(ConvertOperators.ToObjectId.toObjectId("$order_user_id"))
                        .addField("order_shop_obj_id").withValue(ConvertOperators.ToObjectId.toObjectId("$order_shop_id"))
                        .addField("order_status_obj_id").withValue(ConvertOperators.ToObjectId.toObjectId("$order_status_id"))
                        .build(),
                Aggregation.lookup("users", "order_user_obj_id", "_id", "user_info"),
                Aggregation.unwind("user_info", true),

                Aggregation.lookup("shop", "order_shop_obj_id", "_id", "shop_info"),
                Aggregation.unwind("shop_info", true),

                Aggregation.lookup("orderStatus", "order_status_obj_id", "_id", "order_status_info"),
                Aggregation.unwind("order_status_info", true),

                Aggregation.project()
                        .and("_id").as("orderId")
                        .and("order_no").as("orderNo")
                        .and("order_date").as("orderDate")
                        .and("total_price").as("totalPrice")
                        .and("order_user_id").as("userId")
                        .and("$user_info.username").as("userName")
                        .and("order_shop_id").as("shopId")
                        .and("$shop_info.shop_name").as("shopName")
                        .and("$order_status_info.status_name").as("orderStatus")

        );
        AggregationResults<OrderWithDetail> results = mongoTemplate.aggregate(aggregation, "order", OrderWithDetail.class);
        return results.getMappedResults();
    }

    public List<Order> getAllByUserId(String userId) {
        return orderRepository.findByOrderUserId(userId);
    }

    public List<OrderWithProduct> getOrdersWithProductsByUserId(String userId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("order_user_id").is(userId)),

                // 1️⃣ Giải nén mảng order_details để làm việc với từng sản phẩm trong đơn hàng
                Aggregation.unwind("order_details", true),

                // 2️⃣ Convert `order_details.productId` từ String sang ObjectId (nếu cần)
                Aggregation.addFields().addField("order_details.productId")
                        .withValue(ConvertOperators.ToObjectId.toObjectId("$order_details.productId"))
                        .build(),

                // 3️⃣ Join với bảng "product" dựa trên productId
                Aggregation.lookup("product", "order_details.productId", "_id", "product_info"),

                // 4️⃣ Giải nén kết quả product_info (nếu không có thì giữ nguyên)
                Aggregation.unwind("product_info", true),

                // 5️⃣ Thêm các trường từ `product_info` vào `order_details`
                Aggregation.addFields().addField("order_details.productTitle")
                        .withValue("$product_info.product_title")
                        .addField("order_details.productDescription")
                        .withValue("$product_info.product_description")
                        .addField("order_details.imageList")
                        .withValue("$product_info.image_list")
                        .build(),

                // 6️⃣ Gom nhóm lại theo order ID và kết hợp thông tin sản phẩm
                Aggregation.group("_id")
                        .first("order_no").as("orderNo")
                        .first("order_date").as("orderDate")
                        .first("expected_delivery_date").as("expectedDeliveryDate")
                        .first("total_price").as("totalPrice")
                        .first("shipping_fee").as("shippingFee")
                        .first("order_user_id").as("orderUserId")
                        .first("order_status_id").as("orderStatusId")
                        .first("order_shop_id").as("orderShopId")
                        .first("order_payment_method_id").as("orderPaymentMethodId")
                        .first("order_delivery_address_id").as("orderDeliveryAddressId")
                        .push("order_details").as("orderDetails")
        );

        AggregationResults<OrderWithProduct> results = mongoTemplate.aggregate(aggregation, "order", OrderWithProduct.class);
        return results.getMappedResults();
    }

    public List<OrderWithProduct> getOrdersWithProductsByShopId(String shopId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("order_shop_id").is(shopId)),

                // 1️⃣ Giải nén mảng order_details để làm việc với từng sản phẩm trong đơn hàng
                Aggregation.unwind("order_details", true),

                // 2️⃣ Convert `order_details.productId` từ String sang ObjectId (nếu cần)
                Aggregation.addFields().addField("order_details.productId")
                        .withValue(ConvertOperators.ToObjectId.toObjectId("$order_details.productId"))
                        .build(),

                // 3️⃣ Join với bảng "product" dựa trên productId
                Aggregation.lookup("product", "order_details.productId", "_id", "product_info"),

                // 4️⃣ Giải nén kết quả product_info (nếu không có thì giữ nguyên)
                Aggregation.unwind("product_info", true),

                // 5️⃣ Thêm các trường từ `product_info` vào `order_details`
                Aggregation.addFields().addField("order_details.productTitle")
                        .withValue("$product_info.product_title")
                        .addField("order_details.productDescription")
                        .withValue("$product_info.product_description")
                        .addField("order_details.imageList")
                        .withValue("$product_info.image_list")
                        .build(),

                // 6️⃣ Gom nhóm lại theo order ID và kết hợp thông tin sản phẩm
                Aggregation.group("_id")
                        .first("order_no").as("orderNo")
                        .first("order_date").as("orderDate")
                        .first("expected_delivery_date").as("expectedDeliveryDate")
                        .first("total_price").as("totalPrice")
                        .first("order_user_id").as("orderUserId")
                        .first("order_status_id").as("orderStatusId")
                        .first("order_shop_id").as("orderShopId")
                        .first("order_payment_method_id").as("orderPaymentMethodId")
                        .first("order_delivery_address_id").as("orderDeliveryAddressId")
                        .push("order_details").as("orderDetails") // ✅ Lưu toàn bộ order_details sau khi thêm dữ liệu
        );

        AggregationResults<OrderWithProduct> results = mongoTemplate.aggregate(aggregation, "order", OrderWithProduct.class);
        return results.getMappedResults();
    }

    public List<MonthlyRevenueResult> getMonthlyRevenueForYear(String shopId, int year) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("order_shop_id").is(shopId)
                .and("order_status_id").ne(CANCELLED_STATUS_ID)
                .and("order_date").ne(null)
                .andOperator(
                        Criteria.where("order_date").gte(Instant.parse(String.format("%d-01-01T00:00:00.000Z", year)))
                                .lt(Instant.parse(String.format("%d-01-01T00:00:00.000Z", year + 1)))
                )
        );

        UnwindOperation unwindOperation = Aggregation.unwind("order_details");

        ProjectionOperation projectStage = Aggregation.project()
                .and(DateOperators.dateOf("order_date").toString("%Y-%m")).as("month")
                .and("order_details.price").as("price")
                .and("order_details.quantity").as("quantity");

        GroupOperation groupStage = Aggregation.group("month")
                .sum(ArithmeticOperators.Multiply.valueOf("price")
                        .multiplyBy("quantity")).as("totalRevenue");


        SortOperation sortStage = Aggregation.sort(Sort.by(Sort.Direction.ASC, "_id"));

        Aggregation aggregation = Aggregation.newAggregation(matchStage, unwindOperation, projectStage, groupStage, sortStage);

        AggregationResults<MonthlyRevenueResult> result = mongoTemplate.aggregate(aggregation, "order", MonthlyRevenueResult.class);

        return result.getMappedResults();
    }

    public List<DailyRevenueResult> getDailyRevenueForMonth(String shopId, int year, int month) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("order_shop_id").is(shopId)
                .and("order_status_id").ne(CANCELLED_STATUS_ID)
                .and("order_date").ne(null)
                .andOperator(
                        Criteria.where("order_date").gte(Instant.parse(String.format("%d-%02d-01T00:00:00.000Z", year, month)))
                                .lt(Instant.parse(String.format("%d-%02d-01T00:00:00.000Z", year, month + 1)))
                )
        );

        UnwindOperation unwindOperation = Aggregation.unwind("order_details");

        ProjectionOperation projectStage = Aggregation.project()
                .and(DateOperators.dateOf("order_date").toString("%Y-%m-%d")).as("day")
                .and("order_details.price").as("price")
                .and("order_details.quantity").as("quantity");

        GroupOperation groupStage = Aggregation.group("day")
                .sum(ArithmeticOperators.Multiply.valueOf("price")
                        .multiplyBy("quantity")).as("totalRevenue");

        SortOperation sortStage = Aggregation.sort(Sort.by(Sort.Direction.ASC, "_id"));

        Aggregation aggregation = Aggregation.newAggregation(matchStage, unwindOperation, projectStage, groupStage, sortStage);

        AggregationResults<DailyRevenueResult> result = mongoTemplate.aggregate(aggregation, "order", DailyRevenueResult.class);

        return result.getMappedResults();
    }

    public List<Order> create(List<Order> orders) {
        int currentYear = LocalDateTime.now().getYear();
        long baseSeq = sequenceGeneratorService.generateSequence("order_" + currentYear);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            long currentSeq = baseSeq + i;
            String formattedOrderNo = String.format("ORD-%d-%06d", currentYear, currentSeq);
            order.setOrderNo(formattedOrderNo);
            order.setOrderSeq(currentSeq);
            for(OrderDetail orderDetail : order.getOrderDetails()) {
                String productId = orderDetail.getProductId();
                int quantity = orderDetail.getQuantity();
                Map<String, String> selectedOptions = orderDetail.getSelectedOptions();
                productService.updateSoldCount(orderDetail.getProductId(), orderDetail.getQuantity());

                if(selectedOptions == null || selectedOptions.isEmpty()){
                    productService.decreaseBaseQuantity(productId, quantity);
                }else{
                    productService.decreaseVariationStock(productId, selectedOptions, quantity);
                }

            }

        }

        return orderRepository.saveAll(orders);

    }

    public List<OrderTemp> saveTempOrders(List<Order> orders) {
        List<OrderTemp> tempOrders = orders.stream().map(order -> {
            OrderTemp temp = new OrderTemp();
            temp.setOrderDetails(order.getOrderDetails());
            temp.setOrderDate(order.getOrderDate());
            temp.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
            temp.setTotalPrice(order.getTotalPrice());
            temp.setOrderUserId(order.getOrderUserId());
            temp.setOrderStatusId(order.getOrderStatusId());
            temp.setOrderShopId(order.getOrderShopId());
            temp.setOrderPaymentMethodId(order.getOrderPaymentMethodId());
            temp.setOrderDeliveryAddressId(order.getOrderDeliveryAddressId());
            return temp;
        }).toList();

        return orderTempRepository.saveAll(tempOrders);
    }

    public List<Order> convertTempToRealOrders(List<String> tempOrderIds) {
        List<OrderTemp> tempOrders = orderTempRepository.findAllById(tempOrderIds);
        List<Order> realOrders = tempOrders.stream().map(temp -> {
            Order order = new Order();
            order.setOrderDetails(temp.getOrderDetails());
            order.setOrderDate(temp.getOrderDate());
            order.setExpectedDeliveryDate(temp.getExpectedDeliveryDate());
            order.setTotalPrice(temp.getTotalPrice());
            order.setOrderUserId(temp.getOrderUserId());
            order.setOrderStatusId(temp.getOrderStatusId());
            order.setOrderShopId(temp.getOrderShopId());
            order.setOrderPaymentMethodId(temp.getOrderPaymentMethodId());
            order.setOrderDeliveryAddressId(temp.getOrderDeliveryAddressId());
            return order;
        }).toList();

        orderTempRepository.deleteAllById(tempOrderIds);
        return create(realOrders);
    }

    public Order updateOrderStatus(String orderId, String orderStatusId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found" + orderId));
        order.setOrderStatusId(orderStatusId);
        return orderRepository.save(order);
    }

}
