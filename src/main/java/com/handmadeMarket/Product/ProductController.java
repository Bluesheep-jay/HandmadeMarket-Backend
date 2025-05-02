package com.handmadeMarket.Product;

import org.bson.Document;
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

    @GetMapping("/total")
    public long getTotalProducts() {
        return productService.getTotalProduct();
    }

    @PostMapping("/by-id-list")
    public List<Product> getProductsByIdList(@RequestBody List<String> idList) {
        return productService.getByIdList(idList);
    }

//    @GetMapping("/best-sellers")
//    public List<Product> getBestSellingProductsForCurrentMonth() {
//        return productService.getBestSellingProductsForCurrentMonth();
//    }

    @GetMapping("/best-sellers")
    public List<Document> getBestSellingProductsForCurrentMonth() {
        return productService.getBestSellingProductsForCurrentMonth();
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
