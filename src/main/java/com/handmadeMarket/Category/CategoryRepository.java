package com.handmadeMarket.Category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List <Category> findByCategoryParentIdIsNull();
    List<Category> findByCategoryParentId(String parentId);
}
