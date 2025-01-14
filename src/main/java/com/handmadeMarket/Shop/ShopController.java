package com.handmadeMarket.Shop;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private ShopService shopService;

    ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public Shop addShop(@RequestBody Shop shop) {
        return shopService.addShop(shop);
    }

    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{id}")
    public Shop getShopById(@PathVariable String id) {
        return shopService.getShopById(id);
    }

    @PutMapping("/active-status/{id}")
    public Shop updateShopStatus(@PathVariable String id, @RequestBody Map<String, Boolean> isActive) {
        return shopService.updateActiveStatus(id, isActive.get("isActive"));
    }

    @PutMapping("/info/{id}")
    public Shop updateShopInfo(@PathVariable String id, @RequestBody Shop shop) {
        return shopService.updateInfo(id, shop);
    }

//    @PutMapping("/rating/{id}")
//    public Shop updateShopRating(@PathVariable String id, @RequestBody Map<String, Double> ratingObj) {
//        return shopService.updateShopRating(id, ratingObj.get("shopRating"));
//    }


}
