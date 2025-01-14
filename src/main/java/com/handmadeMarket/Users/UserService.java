package com.handmadeMarket.Users;

import com.handmadeMarket.Address.Address;
import com.handmadeMarket.Cart.Cart;
import com.handmadeMarket.Cart.CartService;
import com.handmadeMarket.Exception.DuplicateResourcesException;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Mapper.UserMapper;
import com.handmadeMarket.Role.EnumRole;
import com.handmadeMarket.Users.dto.RegisterUserDto;
import com.handmadeMarket.Users.dto.UpdateUserDto;
import com.handmadeMarket.Users.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CartService cartService;

    public UserService(UserRepository userRepository, UserMapper userMapper, CartService cartService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cartService = cartService;
    }

    public UserResponseDto registerLocalUser(RegisterUserDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email is already use!");
        }
        Users newUser = userMapper.toUsers(registerDto);

        Cart newCart =  cartService.create(new Cart());
        newUser.setCartId(newCart.getId());
        return userMapper.toUserResponseDto(userRepository.save(newUser));
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto getById(String id) {
        return userMapper.toUserResponseDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    public UserResponseDto updateUserInfo(String id, UpdateUserDto updateUserDto) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        Users u = userMapper.updateDtoToUsers(updateUserDto);
        u.setId(id);
        u.setEnumRole(EnumRole.fromValue(updateUserDto.getEnumRole()));
        return userMapper.toUserResponseDto(userRepository.save(u));
    }

    public UserResponseDto updatePoints(String id, UpdateUserDto updateUserDto) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        Users u = userMapper.updateDtoToUsers(updateUserDto);
        u.setId(id);
        u.setEnumRole(EnumRole.fromValue(updateUserDto.getEnumRole()));


        return userMapper.toUserResponseDto(userRepository.save(u));
    }

    @Transactional
    public void updateAddressList(Address address) {
        String userId = address.getAddressOfUserId();
        String addressId = address.getId();
        Optional<Users> existingUser = userRepository.findById(userId);
        if(existingUser.isPresent()) {
            Users user = existingUser.get();
            if(!user.getAddressIdList().contains(addressId)){
                user.getAddressIdList().add(addressId);
            }else{
               throw new DuplicateResourcesException("Address already exists with id: " + addressId);
            }
            userRepository.save(user);
        }else{
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

    }

    @Transactional
    public void deleteAddressIdFromAddressList(Address address){
        Optional<Users> existingUser = userRepository.findById(address.getAddressOfUserId());
        if(existingUser.isPresent()){
            Users user = existingUser.get();
            String addressId = address.getId();
            if(user.getAddressIdList().contains(addressId)){
                user.getAddressIdList().remove(addressId);
            }else{
                throw new ResourceNotFoundException("Address not found with id: " + addressId);
            }
            userRepository.save(user);
        }else {
            throw new ResourceNotFoundException("User not found with id: " + address.getAddressOfUserId());
        }
    }

    public void deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
    }

}
