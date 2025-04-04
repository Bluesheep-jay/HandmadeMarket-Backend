package com.handmadeMarket.ShopRevenue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop-revenue")
public class ShopRevenueController {

    private final ShopRevenueService shopRevenueService;

    @Autowired
    public ShopRevenueController(ShopRevenueService shopRevenueService) {
        this.shopRevenueService = shopRevenueService;
    }

    @GetMapping
    public List<ShopRevenue> getAll() {
        return shopRevenueService.getAll();
    }

    @PostMapping
    public ShopRevenue create(@RequestBody ShopRevenue shopRevenue) {
        return shopRevenueService.create(shopRevenue);
    }

}