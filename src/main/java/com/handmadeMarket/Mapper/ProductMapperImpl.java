package com.handmadeMarket.Mapper;

import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.dto.CreateProductDto;
import com.handmadeMarket.Product.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper{
//    @Override
//    public Product toProduct(CreateProductDto createProductDto) {
//        if ( createProductDto == null ) {
//            return null;
//        }
//        Product product = new Product();
//
//        product.setProductTitle( createProductDto.getProductTitle() );
//        product.setProductDescription( createProductDto.getProductDescription() );
//        product.setBasePrice( createProductDto.getBasePrice() );
//        product.setImageList( createProductDto.getImageList() );
//        product.setOptionList( createProductDto.getOptionList() );
//        product.setQuantity( createProductDto.getQuantity() );
//        product.setHeight( createProductDto.getHeight() );
//        product.setWidth( createProductDto.getWidth() );
//        product.setWeight( createProductDto.getWeight() );
//        product.setCategoryId( createProductDto.getCategoryId() );
//        product.setShopId( createProductDto.getShopId() );
//
//        return product;
//
//    }

}
