package com.handmadeMarket.Product;

import com.handmadeMarket.Product.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable String id){
        return productService.getById(id);
    }

    @GetMapping("/filterByCategoryId/{id}")
    public List<ProductResponseDto> getByCategoryId(@PathVariable String id){
        return productService.getByCategoryId(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable String id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @DeleteMapping("/")
    public void delete(@RequestParam String productId){
        productService.delete( productId);
    }



}
