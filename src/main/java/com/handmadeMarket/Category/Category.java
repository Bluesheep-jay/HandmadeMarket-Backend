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
public class Category {

    @Id
    private String id;

    @TextIndexed(weight = 3)
    @Field(value = "category_name")
    private String categoryName;

    @Field("category_parent_id")
    private String categoryParentId;

    @Field("category_image_url")
    private String categoryImageUrl;
}
