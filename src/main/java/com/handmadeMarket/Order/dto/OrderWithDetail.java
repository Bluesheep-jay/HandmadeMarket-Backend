package com.handmadeMarket.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithDetail {
    private String orderId;
    private String orderNo;
    private Instant orderDate;
    private int totalPrice;
    private String userId;
    private String userName;
    private String shopId;
    private String shopName;
    private String orderStatus;
}