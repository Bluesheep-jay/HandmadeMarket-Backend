package com.handmadeMarket.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByIdIn(List<String> idList);

    List<Product> findByCategoryIdIn(List<String> categoryIdList);
}

