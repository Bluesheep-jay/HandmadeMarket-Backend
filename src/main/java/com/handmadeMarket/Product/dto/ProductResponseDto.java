package com.handmadeMarket.Product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {
    private String id;
    private String productTitle;
    private String productDescription;
    private Double basePrice;
    private List<String> imageList;
    private List<Map<String, Object>> optionList;
    private Integer quantity;
    private String height;
    private String width;
    private String weight;
    private String categoryLevel2Id;
    private double rating;
    private String shopId;

}
