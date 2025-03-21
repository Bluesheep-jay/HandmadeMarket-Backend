package com.handmadeMarket.Cart;

import com.handmadeMarket.Cart.CartItem.CartItem;
import com.handmadeMarket.Cart.dto.CartResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<Cart> getAll() {
        return cartService.getAll();
    }

    @GetMapping("/{cartId}")
    public CartResponse getCartById(@PathVariable String cartId) {
        return cartService.getById(cartId);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.create(cart);
    }

    @PutMapping("/{cartId}")
    public Cart updateCart(@PathVariable String cartId, @RequestBody CartItem cartItem) {
        return cartService.addItemIntoCart(cartId, cartItem);
    }

    @PutMapping("/removeItem/{cartId}/{productId}")
    public Cart removeItem(@PathVariable String cartId, @PathVariable String productId) {
        return cartService.removeItemFromCart(cartId, productId);
    }

    @PutMapping("/updateQuantity/{cartId}")
    public Cart updateItemQuantityInCart(@PathVariable String cartId, @RequestBody Cart cart) {
        return cartService.updateItemQuantityInCart(cartId, cart);
    }

    @PutMapping("/clearCart/{cartId}")
    public Cart clearCart(@PathVariable String cartId) {
        return cartService.clearCart(cartId);
    }

}
