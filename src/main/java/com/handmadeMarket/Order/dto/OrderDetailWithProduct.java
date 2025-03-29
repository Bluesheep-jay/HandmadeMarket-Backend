package com.handmadeMarket.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailWithProduct {
    private String productId;
    private int quantity;
    private double price;
    private Map<String, String> selectedOptions;
    private String productTitle;
    private String productDescription;
    private List<String> imageList;
}
