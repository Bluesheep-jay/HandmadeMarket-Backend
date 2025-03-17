package com.handmadeMarket.Mapper;

import com.handmadeMarket.Address.Address;
import com.handmadeMarket.Address.dto.CreateAddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements AddressMapper{
    @Override
    public Address toAddress(CreateAddressDto createAddressDto) {
        if ( createAddressDto == null ) {
            return null;
        }

        Address address = new Address();

        address.setProvinceId( createAddressDto.getProvinceId());
        address.setDistrictId( createAddressDto.getDistrictId() );
        address.setWardId( createAddressDto.getWardId() );
        address.setProvinceName( createAddressDto.getProvinceName() );
        address.setDistrictName( createAddressDto.getDistrictName() );
        address.setWardName( createAddressDto.getWardName() );
        address.setSpecificAddress( createAddressDto.getSpecificAddress() );
        address.setRecipientName( createAddressDto.getRecipientName() );
        address.setAddressOfUserId( createAddressDto.getAddressOfUserId() );

        return address;
    }
}
