package com.handmadeMarket.Mapper;

import com.handmadeMarket.Product.dto.CreateProductDto;
import com.handmadeMarket.Shop.Shop;
import com.handmadeMarket.Shop.dto.CreateShopDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    Shop toShop(CreateShopDto createShopDto);
}
