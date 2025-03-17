package com.handmadeMarket.Product;

import com.handmadeMarket.Category.CategoryLevel2;
import com.handmadeMarket.Category.CategoryLevel2Repository;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryLevel2Repository categoryLevel2Repository;
    private final MongoTemplate mongoTemplate;

    public ProductService(ProductRepository productRepository,
                          CategoryLevel2Repository categoryLevel2Repository,
                          MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.categoryLevel2Repository = categoryLevel2Repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByCategoryLevel1Id(String categoryLevel1Id) {
        List<CategoryLevel2> categoryLevel2List = categoryLevel2Repository.findByCategoryLevel1Id(categoryLevel1Id);

        // Trích xuất danh sách categoryLevel2Id
        List<String> categoryLevel2Ids = categoryLevel2List.stream()
                .map(CategoryLevel2::getId)
                .collect(Collectors.toList());

        // Truy vấn danh sách sản phẩm theo danh sách categoryLevel2Id
        return productRepository.findByCategoryLevel2Ids(categoryLevel2Ids);
    }

    public Set<Product> getProductBySearchText(String keyword) {
        Set<Product> suggestions = new LinkedHashSet<>();

        Query productQuery = new Query(
                Criteria.where("productTitle").regex(".*" + keyword + ".*", "i")
        );

        List<Product> products = mongoTemplate.find(productQuery, Product.class);
        suggestions.addAll(products);

        String[] keywords = keyword.split("\\s+");

        List<Criteria> andConditions = new ArrayList<>();
        for (String word : keywords) {
            andConditions.add(Criteria.where("productTitle").regex(".*" + word + ".*", "i"));
        }
        Query strictQuery = new Query(new Criteria().andOperator(andConditions.toArray(new Criteria[0])));
        List<Product> exactProducts = mongoTemplate.find(strictQuery, Product.class);
        suggestions.addAll(exactProducts);

        List<Criteria> orCondition = new ArrayList<>();
        for (String word : keywords) {
            orCondition.add(Criteria.where("productTitle").regex(".*" + word + ".*", "i"));
        }
        Query fallbackQuery = new Query(new Criteria().orOperator(orCondition.toArray(new Criteria[0])));

        List<Product> orProducts = mongoTemplate.find(fallbackQuery, Product.class);
        suggestions.addAll(orProducts);

        return suggestions;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with id: " + id)
        );
    }

    public List<Product> getByCategoryLevel2Id(String categoryId) {
        return productRepository.findByCategoryLevel2Id(categoryId);
    }

    public List<Product> getByIdList(List<String> idList) {
        return productRepository.findByIdIn(idList);
    }

    public Product update(String id, Product updatedProduct) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    public Product delete(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found"));
        productRepository.deleteById(productId);
        return product;
    }

}
