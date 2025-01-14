package com.handmadeMarket.Product;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Mapper.ProductMapper;
import com.handmadeMarket.Product.dto.ProductResponseDto;
import com.handmadeMarket.Shop.ShopService;
import com.handmadeMarket.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ShopService shopService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ShopService shopService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.shopService = shopService;
    }

    @Transactional
    public Product create(Product product) {
        Product newProduct = productRepository.save(product);
        shopService.updateProductList(newProduct);
        return newProduct;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public ProductResponseDto getById(String id) {
        return productMapper.toProductResponseDto(
                productRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + id))
        );
    }

    public List<ProductResponseDto> getByCategoryId(String categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream().map(productMapper::toProductResponseDto).collect(Collectors.toList());
    }

    public ProductResponseDto update(String id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct
                -> {
            updatedProduct.setId(id);
            return productMapper.toProductResponseDto(productRepository.save(updatedProduct));
        }).orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));
    }

    public void delete(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found"));
        productRepository.deleteById(productId);
        shopService.deleteProductIdFromProductList(product);
    }

}
