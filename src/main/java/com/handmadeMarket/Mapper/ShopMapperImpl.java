package com.handmadeMarket.Mapper;

import com.handmadeMarket.Shop.Shop;
import com.handmadeMarket.Shop.dto.CreateShopDto;
import org.springframework.stereotype.Component;

@Component
public class ShopMapperImpl implements ShopMapper {
    @Override
    public Shop toShop(CreateShopDto createShopDto) {
        if ( createShopDto == null ) {
            return null;
        }
        Shop shop = new Shop();

        shop.setShopName( createShopDto.getShopName() );
        shop.setPhoneNumber( createShopDto.getPhoneNumber() );
        shop.setFullName( createShopDto.getFullName() );
        shop.setProvinceId( createShopDto.getProvinceId() );
        shop.setDistrictId( createShopDto.getDistrictId() );
        shop.setWardId( createShopDto.getWardId() );
        shop.setProvinceName( createShopDto.getProvinceName() );
        shop.setDistrictName( createShopDto.getDistrictName() );
        shop.setWardName( createShopDto.getWardName() );
        shop.setSpecificAddress( createShopDto.getSpecificAddress() );
        shop.setBusinessType( createShopDto.getBusinessType() );
        shop.setTaxCode( createShopDto.getTaxCode() );
        shop.setIdNumber( createShopDto.getIdNumber() );
        shop.setIdFullName( createShopDto.getIdFullName() );
        shop.setIdFrontImageUrl( createShopDto.getIdFrontImageUrl() );
        shop.setIdBackImageUrl( createShopDto.getIdBackImageUrl() );
        shop.setShopRating( 0.0 );
        shop.setShopIsOpen( true );
        shop.setShopIsActive( true );

        return shop;
    }
}
