package com.handmadeMarket.Category;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id:" + id));
    }

    public Category update(String id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existingCategory -> {
            updatedCategory.setId(id);
            return categoryRepository.save(updatedCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"));
    }

    public void delete(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + id + " not found"));
        categoryRepository.delete(category);
    }
}
