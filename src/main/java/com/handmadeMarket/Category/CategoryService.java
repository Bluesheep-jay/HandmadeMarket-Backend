package com.handmadeMarket.Category;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }


    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findByCategoryParentIdIsNull();
    }

    public List<Category> getSubCategories(String parentId) {
        return categoryRepository.findByCategoryParentId(parentId);
    }

    public List<Category> findAllParentCategories(String categoryId) {
        List<Category> parentCategories = new ArrayList<>();
        parentCategories.add(getById(categoryId));
        findParentCategoriesRecursive(categoryId, parentCategories);
        return parentCategories;
    }

    private void findParentCategoriesRecursive(String categoryId, List<Category> parentCategories) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return; // Không tìm thấy danh mục
        }

        Category category = categoryOpt.get();
        if (category.getCategoryParentId() != null && !category.getCategoryParentId().isEmpty()) {
            Optional<Category> parentOpt = categoryRepository.findById(category.getCategoryParentId());
            if (parentOpt.isPresent()) {
                Category parent = parentOpt.get();
                parentCategories.add(parent);
                findParentCategoriesRecursive(parent.getId(), parentCategories);
            }
        }
    }

    public List<String> getAllCategoryIdsByRootCategoryId(String rootCategoryId) {
        List<String> categoryIds = new ArrayList<>();
        categoryIds.add(rootCategoryId);

        List<Category> subCategories = categoryRepository.findByCategoryParentId(rootCategoryId);
        for(Category subCategory : subCategories) {
            categoryIds.addAll(getAllCategoryIdsByRootCategoryId(subCategory.getId()));
        }
        return categoryIds;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }


    public Category getById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id:" + id));
    }

    public Category update(String id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existingCategoryLevel2 -> {
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
