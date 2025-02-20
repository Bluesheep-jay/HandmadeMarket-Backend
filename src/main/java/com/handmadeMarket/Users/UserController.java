package com.handmadeMarket.Users;

import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Shop.Shop;
import com.handmadeMarket.Users.dto.UpdateUserDto;
import com.handmadeMarket.Users.dto.UserResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable String id){
        return userService.getById(id);
    }
    @GetMapping("/rank/{rankId}")
    public long getUsersByRankId(@PathVariable String rankId) {
        return userService.countUsersByRankId(rankId);
    }

    @GetMapping("/email/{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email){
        return userService.getByEmail(email);
    }

    @GetMapping("wishlist/{id}")
    public List<Product> getWishList(@PathVariable String id){
        return userService.getWishlistProducts(id);
    }

    @GetMapping
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @PutMapping("/update-info/{id}")
    public UserResponseDto updateInfo(@PathVariable String id, @RequestBody UpdateUserDto updateUserDto){
        return userService.updateUserInfo(id, updateUserDto );
    }

    @PutMapping("/update-points/{id}")
    public UserResponseDto updatePoints(@PathVariable String id, @RequestBody UpdateUserDto updateUserDto){
        return userService.updatePoints(id, updateUserDto );
    }

    @PutMapping("/update-wishlist/{id}")
    public List<Product> updateWishList(@PathVariable String id, @RequestBody List<String> updatedWishList){
        return userService.updateWishList(id, updatedWishList);
    }

    @PutMapping("/update-shop-list/{id}")
    public List<Shop> updateFavouriteShopList(@PathVariable String id, @RequestBody List<String> updatedShopList){
        return userService.updateFavouriteShopList(id, updatedShopList);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        userService.deleteUser(id);
    }


}
