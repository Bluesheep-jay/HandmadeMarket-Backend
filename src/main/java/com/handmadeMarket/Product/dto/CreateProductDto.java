package com.handmadeMarket.Product.dto;

import com.handmadeMarket.Product.Variation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    private String productTitle;
    private String categoryLevel2Id;
    private String productDescription;
    private String personalizationDescription;
    private List<String> imageList;
    private double basePrice;
    private int baseQuantity;
    private List<Variation> variationList;
    private String weight;
    private String length;
    private String width;
    private String height;
    private double rating = 0.0;
    private String shopId;

}
