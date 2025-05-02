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

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Field("order_date")
    private Instant orderDate;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Field("expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Field("total_price")
    private int totalPrice;

    @Field("shipping_fee")
    private int shippingFee;

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

    @Field("order_no")
    private String orderNo;

    @Field("order_seq")
    private long orderSeq;
}