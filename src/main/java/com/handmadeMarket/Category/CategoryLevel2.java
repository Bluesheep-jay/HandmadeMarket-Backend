package com.handmadeMarket.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryLevel2 {

    @Id
    private String id;

    @TextIndexed(weight = 3)
    @Field(value = "category_level_2_name")
    private String categoryLevel2Name;

    @Field(value = "attribute_list")
    private List<Map<String, Object>> attributeList;

    @Field("category_level_2_image_url")
    private String categoryLevel2ImageUrl;

    @Field(value = "category_level_1_id")
    private String categoryLevel1Id;

}
