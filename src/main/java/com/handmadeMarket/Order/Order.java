package com.handmadeMarket.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;

    @Field("order_details")
    private List<OrderDetail> orderDetails;

    @Field("order_date")
    private Instant orderDate;

    @Field("expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Field("total_price")
    private int totalPrice;

    @Field("order_user_id")
    private String orderUserId;

    @Field("order_status_id")
    private String orderStatusId;

    @Field("order_shop_id")
    private String orderShopId;

    @Field("order_payment_method_id")
    private String orderPaymentMethodId;

    @Field("order_delivery_address_id")
    private String orderDeliveryAddressId;


// đặt 2 sản phẩm ở cùng 1 cửa hàng thì -> 1 đơn hàng
// đặt 2 sản phẩm ở 2 cửa hàng khác nhau thì -> 2 đơn hàng
}