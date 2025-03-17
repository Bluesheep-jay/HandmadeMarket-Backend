package com.handmadeMarket.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variation {
    private Map<String, String> attributes;
    private double price;
    private int stock;
}
