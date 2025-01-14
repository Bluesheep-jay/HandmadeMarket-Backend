package com.handmadeMarket.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
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
    private LocalDateTime orderDate;

    @Field("expected_delivery_date")
    private String expectedDeliveryDate;

    @Field("total_price")
    private double totalPrice;

    @Field("payment_method_id")
    private String paymentMethodId;

    @Field("user_id")
    private String userId;

    @Field("status_id")
    private String statusId;


}