package com.handmadeMarket.Users;

import com.handmadeMarket.Address.Address;
import com.handmadeMarket.Authentication.JwtService;
import com.handmadeMarket.Authentication.LoginDto;
import com.handmadeMarket.Authentication.RegisterUserDto;
import com.handmadeMarket.Cart.Cart;
import com.handmadeMarket.Cart.CartService;
import com.handmadeMarket.Exception.DuplicateResourcesException;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Mapper.UserMapper;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.ProductService;
import com.handmadeMarket.Users.dto.UpdateUserDto;
import com.handmadeMarket.Users.dto.UserResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final ProductService productService;

    private final BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepository userRepository, UserMapper userMapper,
                       CartService cartService, AuthenticationManager authManager,
                       JwtService jwtService, ProductService productService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cartService = cartService;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.productService = productService;
    }

    public UserResponseDto registerLocalUser(RegisterUserDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email is already use!");
        }

        Users newUser = userMapper.toUsers(registerDto);
        newUser.setPassword(bEncoder.encode(newUser.getPassword()));
        Cart newCart = cartService.create(new Cart());
        newUser.setCartId(newCart.getId());
        return userMapper.toUserResponseDto(userRepository.save(newUser));
    }

    public String verify(LoginDto loginDto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDto.getEmail());
        }
        return "fail";
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto getById(String id) {
        return userMapper.toUserResponseDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
    }

    public UserResponseDto getByEmail(String email) {
        Users users = userRepository.findByEmail(email);
        if (users == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return userMapper.toUserResponseDto(users);
    }


    public List<Product> getWishlistProducts(String id) {
        Users u = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return productService.getByIdList(u.getWishList());
    }

    public void addShopId(String userId, String shopId) {
        Users u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        u.setShopId(shopId);
        userRepository.save(u);
    }

    public List<Product> updateWishList(String id, List<String> updatedWishList) {
        Users u = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        u.setWishList(updatedWishList);
        userMapper.toUserResponseDto(userRepository.save(u));

        return productService.getByIdList(u.getWishList());
    }

    public void updateFavouriteShopList(String id, List<String> updatedShopList) {
        Users u = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        u.setFavouriteShopList(updatedShopList);
        userMapper.toUserResponseDto(userRepository.save(u));

        // note: this method doesn't need to return anything -> call the getByIdList method when needed.
    }

    public long countUsersByRankId(String rankId) {
        return userRepository.countUsersByRankId(rankId);
    }

    public UserResponseDto updateUserInfo(String id, UpdateUserDto updateUserDto) {
        Users existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        Users u = userMapper.updateDtoToUsers(existingUser, updateUserDto);
        u.setId(id);
        return userMapper.toUserResponseDto(userRepository.save(u));
    }

    public UserResponseDto updatePoints(String id, UpdateUserDto updateUserDto) {
        Users existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        Users u = userMapper.updateDtoToUsers(existingUser, updateUserDto);
        u.setId(id);

        return userMapper.toUserResponseDto(userRepository.save(u));
    }

    @Transactional
    public void updateAddressList(Address address) {
        String userId = address.getAddressOfUserId();
        String addressId = address.getId();
        Optional<Users> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            if (!user.getAddressIdList().contains(addressId)) {
                user.getAddressIdList().add(addressId);
            } else {
                throw new DuplicateResourcesException("Address already exists with id: " + addressId);
            }
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

    }

    @Transactional
    public void deleteAddressIdFromAddressList(Address address) {
        Optional<Users> existingUser = userRepository.findById(address.getAddressOfUserId());
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            String addressId = address.getId();
            if (user.getAddressIdList().contains(addressId)) {
                user.getAddressIdList().remove(addressId);
            } else {
                throw new ResourceNotFoundException("Address not found with id: " + addressId);
            }
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + address.getAddressOfUserId());
        }
    }

    public void deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
    }


}
