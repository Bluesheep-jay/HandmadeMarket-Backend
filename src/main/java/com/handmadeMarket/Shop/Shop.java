package com.handmadeMarket.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    @Id
    private String id;

    @Field("shop_name")
    private String shopName;

    @Field("shop_description")
    private String shopDescription;

    @Field("shop_is_open")
    private boolean shopIsOpen = true;

    @Field("shop_is_active")
    private boolean shopIsActive = true;

    @Field("shop_rating")
    private double shopRating = 0.0;

    @Field("user_id")
    private String userId;

    @Field("product_id_list")
    private List<String> productIdList = new ArrayList<>();
}
