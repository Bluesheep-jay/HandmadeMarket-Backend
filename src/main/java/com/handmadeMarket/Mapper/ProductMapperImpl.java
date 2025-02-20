package com.handmadeMarket.Mapper;

import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.dto.CreateProductDto;
import com.handmadeMarket.Product.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper{
    @Override
    public Product toProduct(CreateProductDto createProductDto) {
        if ( createProductDto == null ) {
            return null;
        }
        Product product = new Product();

        product.setProductTitle( createProductDto.getTitle() );
        product.setProductDescription( createProductDto.getDescription() );
        product.setHeight( createProductDto.getHeight());
        product.setWeight( createProductDto.getWeight());
        product.setWidth( createProductDto.getWidth());
        product.setCategoryId( createProductDto.getCategoryId() );
        product.setImageList( createProductDto.getImageList() );
        product.setQuantity( createProductDto.getQuantity() );
        product.setOptionList( createProductDto.getOptionList() );
        product.setBasePrice( createProductDto.getBasePrice() );
        return product;
    }

    @Override
    public ProductResponseDto toProductResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setId( product.getId() );
        productResponseDto.setProductTitle( product.getProductTitle() );
        productResponseDto.setProductDescription( product.getProductDescription() );
        productResponseDto.setBasePrice( product.getBasePrice() );
        productResponseDto.setImageList( product.getImageList() );
        productResponseDto.setOptionList( product.getOptionList() );
        productResponseDto.setQuantity( product.getQuantity() );
        productResponseDto.setHeight( product.getHeight() );
        productResponseDto.setWidth( product.getWidth() );
        productResponseDto.setWeight( product.getWeight() );
        productResponseDto.setCategoryId( product.getCategoryId() );
        productResponseDto.setShopId( product.getShopId() );

        return productResponseDto;
    }
}
