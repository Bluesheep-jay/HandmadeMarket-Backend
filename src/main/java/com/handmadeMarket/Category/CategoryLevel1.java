package com.handmadeMarket.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryLevel1 {
    @Id
    private String id;

    @Field("category_level_1_name")
    private String categoryLevel1Name;

    @Field("category_level_1_image_url")
    private String categoryLevel1ImageUrl;
}
