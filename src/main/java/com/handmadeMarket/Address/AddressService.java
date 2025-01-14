package com.handmadeMarket.Address;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
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
    public Address create( Address address) {
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
}
