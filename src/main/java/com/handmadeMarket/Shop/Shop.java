package com.handmadeMarket.Shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    @Field("phone_number")
    private String phoneNumber;

    @Field("full_name")
    private String fullName;

    @Field("province_id")
    private int provinceId;

    @Field("district_id")
    private int districtId;

    @Field("ward_id")
    private String wardId;

    @Field("specific_address")
    private String specificAddress;

    @Field("business_type")
    private String businessType;

    @Field("tax_code")
    private String taxCode;

    @Field("id_number")
    private String idNumber;

    @Field("id_full_name")
    private String idFullName;

    @Field("id_front_image_url")
    private String idFrontImageUrl;

    @Field("id_back_image_url")
    private String idBackImageUrl;

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
