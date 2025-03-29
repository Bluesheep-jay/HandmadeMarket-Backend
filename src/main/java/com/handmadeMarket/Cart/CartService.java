package com.handmadeMarket.Cart;

import com.handmadeMarket.Cart.CartItem.CartItem;
import com.handmadeMarket.Cart.dto.CartItemResponse;
import com.handmadeMarket.Cart.dto.CartResponse;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.ProductRepository;
import com.handmadeMarket.Shop.Shop;
import com.handmadeMarket.Shop.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository,
                       ShopRepository shopRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    public CartResponse getById(String id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cart not found with id: " + id)
        );

        // Chuyển đổi CartItem -> CartItemResponse
        List<CartItemResponse> cartItems = cart.getCartItemList().stream().map(item -> {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product == null) return null;

            Shop shop = shopRepository.findById(product.getShopId()).orElse(null);

            assert shop != null;
            return new CartItemResponse(
                    item.getProductId(),
                    product.getProductTitle(),
                    product.getImageList().isEmpty() ? null : product.getImageList().getFirst(),
                    item.getQuantity(),
                    product.getShopId(),
                    shop.getShopName(),
                    shop.getShopAvatarUrl(),
                    product.getVariationList(),
                    item.getSelectedOptions(),
                    item.getSubPrice(),
                    product.getPersonalizationDescription(),
                    item.getPersonalizationOfClient(),
                    item.getPersonalizationRequired(),
                    shop.getProvinceId(),
                    shop.getDistrictId(),
                    shop.getWardId()
            );
        }).collect(Collectors.toList());

        return new CartResponse(cart.getId(), cartItems, cart.getCartTotalAmount());
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

    public Cart removeItemFromCart(String cartId, String productId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart existingCart = cartOptional.get();

            CartItem itemToRemove = existingCart.getCartItemList()
                    .stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

            existingCart.setCartTotalAmount(existingCart.getCartTotalAmount() - itemToRemove.getSubPrice());
            existingCart.getCartItemList().remove(itemToRemove);

            return cartRepository.save(existingCart);
        } else {
            throw new ResourceNotFoundException("Cart not found with id: " + cartId);
        }
    }

    public Cart updateItemQuantityInCart(String id, Cart cart) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            Cart existingCart = cartOptional.get();
            existingCart.setCartItemList(cart.getCartItemList());
            existingCart.setCartTotalAmount(cart.getCartTotalAmount());
            return cartRepository.save(existingCart);
        } else {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
    }

    public Cart clearCart(String cartId){
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart existingCart = cartOptional.get();

            existingCart.setCartTotalAmount(0);
            existingCart.setCartItemList(new ArrayList<>());

            return cartRepository.save(existingCart);
        } else {
            throw new ResourceNotFoundException("Cart not found with id: " + cartId);
        }

    }
}
