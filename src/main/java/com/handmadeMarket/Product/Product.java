package com.handmadeMarket.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.TextIndexed;
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

    @TextIndexed(weight = 3)
    @Field(value = "product_title")
    private String productTitle;

    @Field(value = "category_id")
    private String categoryId;

    @Field(value = "product_description")
    private String productDescription;

    @Field("personalization_description")
    private String personalizationDescription;

    @Field(value = "image_list")
    private List<String> imageList;

    @Field(value= "video_url")
    private String videoUrl;

    @Field(value = "base_price")
    private double basePrice;

    @Field(value = "base_quantity")
    private int baseQuantity;

    @Field(value = "variation_list")
    private List<Variation> variationList;
//    [ { name: "size",  options : ["S", "M", "L"], price: 1000, stock: 10},
//      { name: "color", options: ["red", "blue", "green"]}, price: 1000, stock: 10 ]

    private String weight;
    private String length;
    private String width;
    private String height;

    private double rating = 0.0;

    @Field("shop_id")
    private String shopId;

    @Field("approved")
    private boolean approved = false;
}
