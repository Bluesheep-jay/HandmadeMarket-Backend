package com.handmadeMarket.Mapper;

import com.handmadeMarket.Authentication.RegisterUserDto;
import com.handmadeMarket.Users.Users;
import com.handmadeMarket.Users.dto.UpdateUserDto;
import com.handmadeMarket.Users.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toUserResponseDto(Users users) {
        if ( users == null ) {
            return null;
        }
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId( users.getId() );
        userResponseDto.setEmail( users.getEmail() );
        userResponseDto.setUsername( users.getUsername() );
        userResponseDto.setAvatarUrl( users.getAvatarUrl() );
        userResponseDto.setPhoneNumber( users.getPhoneNumber() );
        userResponseDto.setEnumRole( users.getEnumRole() );
        userResponseDto.setShopId( users.getShopId() );
        userResponseDto.setRankId( users.getRankId() );
        userResponseDto.setAddressIdList( users.getAddressIdList() );
        userResponseDto.setCartId( users.getCartId() );
        userResponseDto.setWishList( users.getWishList() );
        userResponseDto.setFavouriteShopList( users.getFavouriteShopList() );
        return userResponseDto;
    }

    @Override
    public Users toUsers(RegisterUserDto registerUserDto) {
        if ( registerUserDto == null ) {
            return null;
        }
        Users users = new Users();
        users.setEmail( registerUserDto.getEmail() );
        users.setPassword( registerUserDto.getPassword() );
        users.setEnumRole( registerUserDto.getEnumRole() );
        return users;
    }

    @Override
    public Users updateDtoToUsers(Users exUser, UpdateUserDto updateUserDto) {
        if ( updateUserDto == null ) {
            return null;
        }
        
        exUser.setUsername( updateUserDto.getUsername() );
        exUser.setAvatarUrl( updateUserDto.getAvatarUrl() );
        exUser.setPhoneNumber( updateUserDto.getPhoneNumber() );
        return exUser;
    }
}
