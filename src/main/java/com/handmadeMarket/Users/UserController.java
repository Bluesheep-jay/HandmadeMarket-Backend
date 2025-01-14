package com.handmadeMarket.Users;

import com.handmadeMarket.Users.dto.RegisterUserDto;
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

    @PostMapping
    public UserResponseDto register(@RequestBody RegisterUserDto registerUserDto){
        System.out.println(registerUserDto.toString());
        return userService.registerLocalUser(registerUserDto);
    }

    @GetMapping
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable String id){
        return userService.getById(id);
    }
    @PutMapping("/update-info/{id}")
    public UserResponseDto updateInfo(@PathVariable String id, @RequestBody UpdateUserDto updateUserDto){
        return userService.updateUserInfo(id, updateUserDto );
    }

    @PutMapping("/update-points/{id}")
    public UserResponseDto updatePoints(@PathVariable String id, @RequestBody UpdateUserDto updateUserDto){
        return userService.updatePoints(id, updateUserDto );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        userService.deleteUser(id);
    }


}
