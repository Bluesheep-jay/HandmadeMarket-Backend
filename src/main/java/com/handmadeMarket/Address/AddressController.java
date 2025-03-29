package com.handmadeMarket.Address;

import com.handmadeMarket.Address.dto.CreateAddressDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/user/{userId}")
    public List<Address> getAddressListByUserId(@PathVariable String userId) {
        return addressService.getAddressListByUserId(userId);
    }

    @GetMapping("/{id}")
    public Address getAddress(@PathVariable String id) {
        return addressService.getById(id);
    }

    @GetMapping
    public List<Address> getAddresses() {
        return addressService.getAll();
    }

    @PostMapping
    public Address createAddress(@RequestBody CreateAddressDto address) {

        return addressService.create(address);
    }

    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable String id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable String id) {
        addressService.delete(id);
    }
}
