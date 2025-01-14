package com.handmadeMarket.Cart;

import com.handmadeMarket.Cart.CartItem.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private String id;

    @Field("cart_items")
    private List<CartItem> cartItemList = new ArrayList<>();

    @Field("cart_total_amount")
    private double cartTotalAmount = 0.0;
}
