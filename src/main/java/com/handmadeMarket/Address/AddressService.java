package com.handmadeMarket.Address;

import com.handmadeMarket.Address.dto.CreateAddressDto;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Mapper.AddressMapper;
import com.handmadeMarket.Users.UserRepository;
import com.handmadeMarket.Users.UserService;
import com.handmadeMarket.Users.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    AddressService(AddressRepository addressRepository, UserService userService,
                   AddressMapper addressMapper, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.addressMapper = addressMapper;
        this.userRepository = userRepository;
    }


    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address getById(String id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address with id: " + id + " not found")
        );
    }

    @Transactional
    public Address create(CreateAddressDto addressDto) {

        Address address = addressMapper.toAddress(addressDto);
        Address savedAddress = addressRepository.save(address);
        userService.updateAddressList(savedAddress);
        return savedAddress;
    }

    public Address update(String id, Address updatedAddress) {
        addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address with id: " + id + " not found")
        );
        updatedAddress.setId(id);
        return addressRepository.save(updatedAddress);
    }

    @Transactional
    public void delete(String id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address with id: " + id + " not found")
        );
        addressRepository.deleteById(id);
        userService.deleteAddressIdFromAddressList(address);
    }

    public List<Address> getAddressListByUserId(String userId) {
        Optional<Users> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            List<String> addressIds = user.getAddressIdList();

            if (addressIds == null || addressIds.isEmpty()) {
                return Collections.emptyList();
            }

            return addressRepository.findByIdIn(addressIds);
        }

        return Collections.emptyList();
    }
}
