package com.handmadeMarket.Seaching;

import com.handmadeMarket.Category.Category;
import com.handmadeMarket.Product.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class SearchSuggestionRepository {
    private final MongoTemplate mongoTemplate;

    public SearchSuggestionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<String> getSearchSuggestions(String keyword){
        Set<String> uniqueSuggestions = new LinkedHashSet<>();

        Query categoryQuery = new Query(
                Criteria.where("categoryName").regex(".*" + keyword + ".*", "i")
        ).limit(6);
        categoryQuery.fields().include("categoryName");

        List<Category> categories = mongoTemplate.find(categoryQuery, Category.class);
        uniqueSuggestions.addAll(
                categories.stream()
                        .map(Category::getCategoryName)
                        .collect(Collectors.toList())
        );

        if(uniqueSuggestions.size() < 6){
            String[] keywords = keyword.split("\\s+");

            List<Criteria> criteriaList = new ArrayList<>();
            for (String word: keywords){
                criteriaList.add(Criteria.where("categoryName").regex(".*" + word + ".*", "i"));
            }

            Query fallbackQuery = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])))
                    .limit(6 - uniqueSuggestions.size());
            fallbackQuery.fields().include("categoryName");

            List<Category> moreCate = mongoTemplate.find(fallbackQuery, Category.class);
            for(Category category: moreCate){
                uniqueSuggestions.add(category.getCategoryName());
            }
        }

        if (uniqueSuggestions.size() < 6) {
            Query productQuery = new Query(
                    Criteria.where("productTitle").regex(".*" + keyword + ".*", "i")
            ).limit(6 - uniqueSuggestions.size());
            productQuery.fields().include("productTitle");

            List<Product> products = mongoTemplate.find(productQuery, Product.class);
            for (Product product : products) {
                uniqueSuggestions.add(product.getProductTitle());
            }
        }

        if(uniqueSuggestions.size() < 6){
            String[] keywords = keyword.split("\\s+");

            List<Criteria> criteriaList = new ArrayList<>();
            for (String word: keywords){

                criteriaList.add(Criteria.where("productTitle").regex(".*" + word + ".*", "i"));
            }

            Query fallbackQuery = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])))
                    .limit(6 - uniqueSuggestions.size());
            fallbackQuery.fields().include("productTitle");

            List<Product> moreProducts = mongoTemplate.find(fallbackQuery, Product.class);
            for(Product product: moreProducts){
                uniqueSuggestions.add(product.getProductTitle());
            }
        }
        return new ArrayList<>(uniqueSuggestions);
    }




//    public List<String> getSearchSuggestions(String keyword) {
//        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(keyword);
//        Query query = new Query(criteria).limit(5);
//        query.fields().include("categoryLevel2Name");
//
//        List<CategoryLevel2> categories = mongoTemplate.find(query, CategoryLevel2.class);
//
//        return categories.stream()
//                .map(CategoryLevel2::getCategoryLevel2Name)
//                .collect(Collectors.toList());
//    }
}
