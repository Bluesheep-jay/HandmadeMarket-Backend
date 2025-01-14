package com.handmadeMarket.Order;

import org.springframework.data.mongodb.core.mapping.Field;

public class OrderDetail {
    @Field("product_id")
    private String productId;
    private int quantity;
    private double price;
}
