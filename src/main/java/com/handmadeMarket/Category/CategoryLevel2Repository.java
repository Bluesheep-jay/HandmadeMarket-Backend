package com.handmadeMarket.Category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryLevel2Repository extends MongoRepository<CategoryLevel2, String> {
    List<CategoryLevel2> findByCategoryLevel1Id(String categoryLevel1Id);
}
