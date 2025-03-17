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

    @PostMapping("/level1")
    public CategoryLevel1 create(@RequestBody CategoryLevel1 newCategoryLevel2){
        return categoryService.createLevel1(newCategoryLevel2);
    }

    @GetMapping("/level1")
    public List<CategoryLevel1> getAllLevel1(){
        return categoryService.getAllLevel1();
    }

/// ------ LEVEL 222222222 -----
    @PostMapping("/level2")
    public CategoryLevel2 create(@RequestBody CategoryLevel2 newCategoryLevel2){
        return categoryService.createLevel2(newCategoryLevel2);
    }

    @GetMapping("/level2-by-level1/{level1Id}")
    public List<CategoryLevel2> getAllLevel2ByLevel1(@PathVariable String level1Id){
        return categoryService.getAllLevel2ByLevel1(level1Id);
    }

    @GetMapping("/level2")
    public List<CategoryLevel2> getAll(){
        return categoryService.getAllLevel2();
    }

    @GetMapping("/level2-id/{id}")
    public CategoryLevel2 getById(@PathVariable String id){
        return categoryService.getByIdLevel2(id);
    }

    @PutMapping("/level2/{id}")
    public CategoryLevel2 update(@PathVariable String id, @RequestBody CategoryLevel2 updatedCategoryLevel2){
        return categoryService.updateLevel2(id, updatedCategoryLevel2);
    }

    @DeleteMapping("/level2/{id}")
    public void delete(@PathVariable String id){
        categoryService.deleteLevel2(id);
    }
}
