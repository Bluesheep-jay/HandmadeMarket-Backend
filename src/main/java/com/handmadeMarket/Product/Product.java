package com.handmadeMarket.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    @Field(value = "product_title")
    private String productTitle;
    @Field(value = "product_description")
    private String productDescription;

    @Field(value = "base_price")
    private double basePrice;

    @Field(value = "image_list")
    private List<String> imageList;

    @Field(value = "option_list")
    private List<Map<String, Object>> optionList;

    private int quantity;
    private String height;
    private String width;
    private String weight;
    @Field(value = "category_id")
    private String categoryId;

    @Field("shop_id")
    private String shopId;
}
