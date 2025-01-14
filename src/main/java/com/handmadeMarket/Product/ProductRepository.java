package com.handmadeMarket.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{'categoryId': ?0}")
    List<Product> findByCategoryId(String categoryId);
}
