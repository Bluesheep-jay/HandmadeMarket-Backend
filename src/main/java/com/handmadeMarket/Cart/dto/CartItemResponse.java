package com.handmadeMarket.Cart.dto;

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
public class CartItemResponse {
    private String productId;
    private String productTitle;
    private String productImage;
    private int quantity;
    private String shopId;
    private String shopName;
    private String shopAvatarUrl;
    private List<Variation> variationList;
    private Map<String, String> selectedOptions;
    private double subPrice;
    private String personalizationDescriptionOfProduct;
    private String personalizationOfClient;
    private Boolean personalizationRequired;
    private int provinceId;
    private int districtId;
    private String wardId;
}
