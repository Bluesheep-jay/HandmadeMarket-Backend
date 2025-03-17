package com.handmadeMarket.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByIdIn(List<String> idList);

    @Query("{ 'category_level_2_id' : { $in: ?0 } }")
    List<Product> findByCategoryLevel2Ids(List<String> categoryLevel2Ids);

    List<Product> findByCategoryLevel2Id(String categoryId);
}
