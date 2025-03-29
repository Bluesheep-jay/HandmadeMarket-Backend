package com.handmadeMarket.Category;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @PostMapping()
    public Category create(@RequestBody Category newCategory){
        return categoryService.create(newCategory);
    }

    @GetMapping("/roots")
    public List<Category> getRootCategories() {
        return categoryService.getRootCategories();
    }

    @GetMapping("/subcategories/{parentId}")
    public List<Category> getSubCategories(@PathVariable String parentId) {
        return categoryService.getSubCategories(parentId);
    }

    @GetMapping("/all-parents/{categoryId}")
    public List<Category> getParentCategories(@PathVariable String categoryId) {
        return categoryService.findAllParentCategories(categoryId);
    }


    @GetMapping()
    public List<Category> getAll(){
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable String id){
        return categoryService.getById(id);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable String id, @RequestBody Category updatedCategory){
        return categoryService.update(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        categoryService.delete(id);
    }
}
