package com.handmadeMarket.Admin;

import com.handmadeMarket.Shop.Shop;
import com.handmadeMarket.Shop.ShopService;
import com.handmadeMarket.Users.UserService;
import com.handmadeMarket.Users.Users;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final ShopService shopService;

    public AdminController(UserService userService, ProductService productService,
                           ShopService shopService) {
        this.userService = userService;
        this.productService = productService;
        this.shopService = shopService;
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public Users updateUser(@PathVariable String id, @RequestBody Users user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PutMapping("/products/{id}/approve")
    public Product approveProduct(@PathVariable String id) {
        return productService.approveProduct(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }


    @PutMapping("/open-shops/{id}/")
    public void approveShop(@PathVariable String id) {
        shopService.approveShop(id);
    }

    @DeleteMapping("/close-shops/{id}/")
    public void closeShop(@PathVariable String id) {
        shopService.closeShop(id);
    }
}