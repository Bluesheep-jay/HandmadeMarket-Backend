package com.handmadeMarket.Authentication;

import com.handmadeMarket.Users.UserService;
import com.handmadeMarket.Users.dto.UserResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody RegisterUserDto registerUserDto){
        return userService.registerLocalUser(registerUserDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        return userService.verify(loginDto);
    }
}
