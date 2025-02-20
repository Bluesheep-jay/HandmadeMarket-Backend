package com.handmadeMarket.Mapper;

import com.handmadeMarket.Authentication.RegisterUserDto;
import com.handmadeMarket.Users.Users;
import com.handmadeMarket.Users.dto.UpdateUserDto;
import com.handmadeMarket.Users.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(Users users);

    Users toUsers(RegisterUserDto registerUserDto);

    Users updateDtoToUsers(Users user, UpdateUserDto updateUserDto);
}
