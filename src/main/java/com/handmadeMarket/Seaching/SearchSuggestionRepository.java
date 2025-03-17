package com.handmadeMarket.Seaching;

import com.handmadeMarket.Category.CategoryLevel2;
import com.handmadeMarket.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
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

    public List<String> getSearchSuggestions2(String keyword) {
        List<String> suggestions = new ArrayList<>();

        // 1. Tìm kiếm chính xác cụm từ đầy đủ trong categoryLevel2Name
        Query categoryQuery = new Query(
                Criteria.where("categoryLevel2Name").regex(".*" + keyword + ".*", "i")
        ).limit(6);
        categoryQuery.fields().include("categoryLevel2Name");

        List<CategoryLevel2> categories = mongoTemplate.find(categoryQuery, CategoryLevel2.class);
        suggestions.addAll(
                categories.stream()
                        .map(CategoryLevel2::getCategoryLevel2Name)
                        .collect(Collectors.toList())
        );

        // Nếu chưa đủ 6 kết quả, tiếp tục tìm trong Product
        if (suggestions.size() < 6) {
            Query productQuery = new Query(
                    Criteria.where("productTitle").regex(".*" + keyword + ".*", "i")
            ).limit(6 - suggestions.size());
            productQuery.fields().include("productTitle");

            List<Product> products = mongoTemplate.find(productQuery, Product.class);
            suggestions.addAll(
                    products.stream()
                            .map(Product::getProductTitle)
                            .collect(Collectors.toList())
            );
        }

        // 2. Nếu vẫn chưa đủ kết quả, tách keyword thành từng từ riêng lẻ và tìm theo từng từ
        if (suggestions.size() < 6) {
            String[] keywords = keyword.split("\\s+");
            List<Criteria> criteriaList = new ArrayList<>();

            for (String word : keywords) {
                criteriaList.add(Criteria.where("productTitle").regex(".*" + word + ".*", "i"));
            }

            Query fallbackQuery = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])))
                    .limit(6 - suggestions.size());
            fallbackQuery.fields().include("productTitle");

            List<Product> moreProducts = mongoTemplate.find(fallbackQuery, Product.class);
            suggestions.addAll(
                    moreProducts.stream()
                            .map(Product::getProductTitle)
                            .collect(Collectors.toList())
            );
        }

        return suggestions;
    }

    public List<String> getSearchSuggestions(String keyword){
        Set<String> uniqueSuggestions = new LinkedHashSet<>();

        Query categoryQuery = new Query(
                Criteria.where("categoryLevel2Name").regex(".*" + keyword + ".*", "i")
        ).limit(6);
        categoryQuery.fields().include("categoryLevel2Name");

        List<CategoryLevel2> categories = mongoTemplate.find(categoryQuery, CategoryLevel2.class);
        uniqueSuggestions.addAll(
                categories.stream()
                        .map(CategoryLevel2::getCategoryLevel2Name)
                        .collect(Collectors.toList())
        );

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
