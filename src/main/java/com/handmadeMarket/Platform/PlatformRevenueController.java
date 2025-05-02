package com.handmadeMarket.Platform;

import com.handmadeMarket.Product.ProductService;
import com.handmadeMarket.Shop.ShopService;
import com.handmadeMarket.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.handmadeMarket.Users.Users;
import com.handmadeMarket.Product.Product;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class PlatformRevenueController {

    private final UserService userService;
    private final ProductService productService;
    private final ShopService shopService;
    private final PlatformRevenueService platformRevenueService;

    @Autowired
    public PlatformRevenueController(PlatformRevenueService platformRevenueService,
                                     UserService userService, ProductService productService,
                                    ShopService shopService) {
        this.platformRevenueService = platformRevenueService;
        this.userService = userService;
        this.productService = productService;
        this.shopService = shopService;
    }

    @GetMapping("/platform-revenue/monthly-for-year/{year}")
    public Map<Integer, Double> getMonthlyRevenueForYear(@PathVariable int year) {
        return platformRevenueService.getMonthlyRevenueForYear(year);
    }

    @GetMapping("/platform-revenue/{month}/{year}")
    public double getAllPlatformRevenue(@PathVariable int month, @PathVariable int year) {
        return platformRevenueService.getTotalRevenueOfPlatform(month, year);
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


    @PutMapping("/approve-shops/{id}/")
    public void approveShop(@PathVariable String id) {
        shopService.approveShop(id);
    }

    @PutMapping("/disapprove-shops/{id}/")
    public void disapproveShop(@PathVariable String id) {
        shopService.disapproveShop(id);
    }

    @DeleteMapping("/delete-shops/{id}")
    public void deleteShop(@PathVariable String id) {
        shopService.deleteShop(id);
    }

}