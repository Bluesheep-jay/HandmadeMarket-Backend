package com.handmadeMarket.Product;

import com.handmadeMarket.Category.Category;
import com.handmadeMarket.Category.CategoryService;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Review.Review;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final MongoTemplate mongoTemplate;

    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService,
                          MongoTemplate mongoTemplate) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
        this.categoryService = categoryService;
    }

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }


    public List<Product> getProductsByRootCategoryId(String rootCategoryId) {
        List<String> allCategoryIds = new ArrayList<>(
                categoryService.getAllCategoryIdsByRootCategoryId(rootCategoryId));
        return productRepository.findByCategoryIdIn(allCategoryIds);
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

    public List<Product> getByProductIdList(List<String> productIds) {
        return productRepository.findAllById(productIds);
    }

    public List<Product> getByIdList(List<String> idList) {
        return productRepository.findByIdIn(idList);
    }

    public Product update(String id, Product updatedProduct) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    public Product updateRating(String id, int rating, List<Review> reviews) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + id + " not found"));

        double newAverageRating;
        if (reviews.isEmpty()) {
            newAverageRating = rating;
        } else {
            double totalRating = reviews.stream().mapToInt(Review::getReviewRating).sum() + rating;
            newAverageRating = totalRating / (reviews.size() + 1);
        }
        product.setRating(newAverageRating);
        return productRepository.save(product);
    }

    public Product delete(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found"));
        productRepository.deleteById(productId);
        return product;
    }

}
