package com.handmadeMarket.Cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private String id;
    private List<CartItemResponse> cartItems;
    private double cartTotalAmount;
}