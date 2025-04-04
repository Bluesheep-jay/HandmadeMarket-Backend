package com.handmadeMarket.Cart.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Field("product_id")
    private String productId;

    @Field("quantity")
    private int quantity;

    @Field("selected_options")
    private Map<String, String> selectedOptions;

    @Field("subtotal_price")
    private double subPrice;

    @Field("personalization_of_client")
    private String personalizationOfClient;

    @Field("personalization_required")
    private Boolean personalizationRequired;
}
