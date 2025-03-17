package com.handmadeMarket.Mapper;

import com.handmadeMarket.Product.dto.CreateProductDto;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProductMapper {
//    Product toProduct(CreateProductDto createProductDto);
}
