package com.handmadeMarket.Product;

import com.handmadeMarket.Product.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController( ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public Product getById(@PathVariable String id){
        return productService.getById(id);
    }

    @GetMapping("/searchText")
    public Set<Product> searchProducts(@RequestParam String q) {
        return productService.getProductBySearchText(q);
    }

    @GetMapping("/by-category-level-1/{categoryLevel1Id}")
    public List<Product> getProductsByCategoryLevel1Id(@PathVariable String categoryLevel1Id) {
        return productService.getProductsByCategoryLevel1Id(categoryLevel1Id);
    }

    @GetMapping("/by-category-level-2/{categoryLevel2Id}")
    public List<Product> getByCategoryLevel2Id(@PathVariable String categoryLevel2Id){
        return productService.getByCategoryLevel2Id(categoryLevel2Id);
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }



    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @DeleteMapping("/")
    public void delete(@RequestParam String productId){
        productService.delete( productId);
    }



}
