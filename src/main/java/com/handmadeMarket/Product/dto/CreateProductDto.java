package com.handmadeMarket.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    private String title;
    private String description;
    private double basePrice;
    private List<String> imageList;
    private List<Map<String, Object>> optionList;
    private int quantity;
    private String height;
    private String width;
    private String weight;
    private String categoryId;
}
