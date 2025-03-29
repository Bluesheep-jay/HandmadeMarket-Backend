package com.handmadeMarket.Product;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        return productService.getById(id);
    }

    @GetMapping("/searchText")
    public Set<Product> searchProducts(@RequestParam String q) {
        return productService.getProductBySearchText(q);
    }

    @GetMapping("/by-root-category/{rootCategoryId}")
    public List<Product> getProductsByRootCategory(@PathVariable String rootCategoryId) {
        return productService.getProductsByRootCategoryId(rootCategoryId);
    }


    @PostMapping("/by-id-list")
    public List<Product> getProductsByIdList(@RequestBody List<String> idList) {
        return productService.getByIdList(idList);
    }


    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }


    @PutMapping("/update-product/{productId}")
    public Product update(@PathVariable String productId, @RequestBody Product product) {
        return productService.update(productId, product);
    }

    @DeleteMapping("/")
    public void delete(@RequestParam String productId) {
        productService.delete(productId);
    }


}
