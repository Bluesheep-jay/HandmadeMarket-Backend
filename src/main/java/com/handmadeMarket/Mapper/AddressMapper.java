package com.handmadeMarket.Mapper;

import com.handmadeMarket.Address.Address;
import com.handmadeMarket.Address.dto.CreateAddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toAddress(CreateAddressDto createAddressDto);
}
