package com.handmadeMarket.Category;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Product.Product;
import com.handmadeMarket.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryLevel1Repository categoryLevel1Repository;
    private final CategoryLevel2Repository categoryLevel2Repository;
    public CategoryService(CategoryLevel1Repository categoryLevel1Repository,
                           CategoryLevel2Repository categoryLevel2Repository
                          ) {
        this.categoryLevel2Repository = categoryLevel2Repository;
        this.categoryLevel1Repository = categoryLevel1Repository;
    }

    public CategoryLevel1 createLevel1(CategoryLevel1 categoryLevel1) {
        return categoryLevel1Repository.save(categoryLevel1);
    }
    public List<CategoryLevel1> getAllLevel1() {
        return categoryLevel1Repository.findAll();
    }




    /// // ========================= LEVEL 2 ========================= ///


    public CategoryLevel2 createLevel2(CategoryLevel2 categoryLevel2) {
        return categoryLevel2Repository.save(categoryLevel2);
    }

    public List<CategoryLevel2> getAllLevel2() {
        return categoryLevel2Repository.findAll();
    }

    public List<CategoryLevel2> getAllLevel2ByLevel1(String level1Id) {
        return categoryLevel2Repository.findByCategoryLevel1Id(level1Id);
    }

    public CategoryLevel2 getByIdLevel2(String id) {
        return categoryLevel2Repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id:" + id));
    }

    public CategoryLevel2 updateLevel2(String id, CategoryLevel2 updatedCategoryLevel2) {
        return categoryLevel2Repository.findById(id).map(existingCategoryLevel2 -> {
            updatedCategoryLevel2.setId(id);
            return categoryLevel2Repository.save(updatedCategoryLevel2);
        }).orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"));
    }

    public void deleteLevel2(String id) {
        CategoryLevel2 categoryLevel2 = categoryLevel2Repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"));
        categoryLevel2Repository.delete(categoryLevel2);
    }
}
