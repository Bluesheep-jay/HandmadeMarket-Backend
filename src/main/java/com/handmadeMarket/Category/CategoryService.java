package com.handmadeMarket.Category;

import com.handmadeMarket.Exception.ResourceNotFoundException;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MongoTemplate mongoTemplate;

    public CategoryService(
            CategoryRepository categoryRepository,
            MongoTemplate mongoTemplate
    ) {
        this.categoryRepository = categoryRepository;
        this.mongoTemplate = mongoTemplate;
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

    public List<Document> getTop5BestSellingProductCategoriesInAMonth(int month, int year) {
        Instant startDate = Instant.parse(String.format("%d-%02d-01T00:00:00.000Z", year, month));
        Instant endDate = month == 12
                ? Instant.parse(String.format("%d-01-01T00:00:00.000Z", year + 1))
                : Instant.parse(String.format("%d-%02d-01T00:00:00.000Z", year, month + 1));

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("order_date").gte(startDate).lt(endDate)),

                Aggregation.unwind("order_details", true),
                Aggregation.addFields().addField("order_details.productId")
                        .withValue(ConvertOperators.ToObjectId.toObjectId("$order_details.productId"))
                        .build(),

                Aggregation.lookup("product", "order_details.productId", "_id", "product_info"),
                Aggregation.unwind("product_info", true),

                Aggregation.addFields().addField("categoryObjId")
                        .withValue(ConvertOperators.ToObjectId.toObjectId("$product_info.category_id"))
                        .build(),
                Aggregation.lookup("category", "categoryObjId", "_id", "category_info"),
                Aggregation.unwind("category_info", true),

                //add category_name
                Aggregation.group("$product_info.category_id")
                        .first("$category_info.category_name").as("categoryName")
                        .first("$category_info.category_image_url").as("categoryImageUrl")
                        .sum("order_details.quantity").as("totalQuantitySold"),

                Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalQuantitySold")),

                Aggregation.limit(5)
        );

        // Execute the aggregation
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "order", Document.class);

        return results.getMappedResults();
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
