package com.handmadeMarket.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
    private String productId;
    private int quantity;
    private double price;
    private Map<String, String> selectedOptions;
    private String personalizationOfClient;
    private Boolean personalizationRequired;
}
