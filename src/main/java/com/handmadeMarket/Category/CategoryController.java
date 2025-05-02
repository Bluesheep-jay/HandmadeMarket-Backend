package com.handmadeMarket.Category;

import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    //getTop5BestSellingProductCategoriesInAMonth
    @GetMapping("/top5/{month}/{year}")
    public List<Document> getTop5BestSellingProductCategoriesInAMonth(@PathVariable int month, @PathVariable int year) {
        return categoryService.getTop5BestSellingProductCategoriesInAMonth(month, year);
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
