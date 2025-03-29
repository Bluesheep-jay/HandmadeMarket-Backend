package com.handmadeMarket.Shop;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
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

    @Field("shop_avatar_url")
    private String shopAvatarUrl;

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

    @Field("province_name")
    private String provinceName;

    @Field("district_name")
    private String districtName;

    @Field("ward_name")
    private String wardName;

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

    @Field("shop_created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @CreatedDate
    private Instant shopCreatedAt;

    @Field("user_id")
    private String userId;

    @Field("product_id_list")
    private List<String> productIdList = new ArrayList<>();

    public Shop(Shop shop){
        this.id = shop.getId(); 
        this.shopName = shop.getShopName();
        this.shopDescription = shop.getShopDescription();
        this.shopAvatarUrl = shop.getShopAvatarUrl();
        this.phoneNumber = shop.getPhoneNumber();
        this.fullName = shop.getFullName();
        this.provinceId = shop.getProvinceId();
        this.districtId = shop.getDistrictId();
        this.wardId = shop.getWardId();
        this.provinceName = shop.getProvinceName();
        this.districtName = shop.getDistrictName();
        this.wardName = shop.getWardName();
        this.specificAddress = shop.getSpecificAddress();
        this.businessType = shop.getBusinessType();
        this.taxCode = shop.getTaxCode();
        this.idNumber = shop.getIdNumber();
        this.idFullName = shop.getIdFullName();
        this.idFrontImageUrl = shop.getIdFrontImageUrl();
        this.idBackImageUrl = shop.getIdBackImageUrl();
        this.shopIsOpen = shop.isShopIsOpen();
        this.shopIsActive = shop.isShopIsActive();
        this.shopRating = shop.getShopRating();
        this.userId = shop.getUserId();
        this.productIdList = shop.getProductIdList();
    }
}
