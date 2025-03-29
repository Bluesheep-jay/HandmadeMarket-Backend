package com.handmadeMarket.Order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithProduct {
    private String id;
    private List<OrderDetailWithProduct> orderDetails;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant orderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")

    private Instant expectedDeliveryDate;
    private int totalPrice;
    private String orderUserId;
    private String orderStatusId;
    private String orderShopId;
    private String orderPaymentMethodId;
    private String orderDeliveryAddressId;
}
