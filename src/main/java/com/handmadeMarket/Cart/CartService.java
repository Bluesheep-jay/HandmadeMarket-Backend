package com.handmadeMarket.Cart;

import com.handmadeMarket.Cart.CartItem.CartItem;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    public Cart getById(String id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with id: " + id)
        );
    }

    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart addItemIntoCart(String id, CartItem cartItem) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        productRepository.findById(cartItem.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id: " + cartItem.getProductId())
        );
        if (cartOptional.isPresent()) {
            Cart existingCart = cartOptional.get();

            boolean isProductInCart = existingCart.getCartItemList().stream()
                    .anyMatch(item -> item.getProductId().equals(cartItem.getProductId())
                            && item.getSelectedOptions().equals(cartItem.getSelectedOptions()));
            if (!isProductInCart) {
                existingCart.getCartItemList().add(cartItem);
                existingCart.setCartTotalAmount(existingCart.getCartTotalAmount() + cartItem.getSubPrice());

                return cartRepository.save(existingCart);
            } else {
                throw new IllegalArgumentException("Product already exists in cart");
            }
        } else {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
    }

    public Cart removeItemFromCart(String cartId, CartItem itemToRemove) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart existingCart = cartOptional.get();
            existingCart.setCartTotalAmount(existingCart.getCartTotalAmount() - itemToRemove.getSubPrice());
            existingCart.getCartItemList().remove(itemToRemove);

            return cartRepository.save(existingCart);
        }else {
            throw new ResourceNotFoundException("Cart not found with id: " + cartId);
        }
    }

    public Cart updateItemQuantityInCart(String id, Cart cart){
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if(cartOptional.isPresent()){
            Cart existingCart = cartOptional.get();
            existingCart.setCartItemList(cart.getCartItemList());
            existingCart.setCartTotalAmount(cart.getCartTotalAmount());
            return cartRepository.save(existingCart);
        }else {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
    }
}
