package com.handmadeMarket.Shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateShopDto {
    private String shopName;
    private String phoneNumber;
    private String fullName;
    private int provinceId;
    private int districtId;
    private String wardId;
    private String provinceName;
    private String districtName;
    private String wardName;
    private String specificAddress;
    private String businessType;
    private String taxCode;
    private String idNumber;
    private String idFullName;
    private String idFrontImageUrl;
    private String idBackImageUrl;
    private String userId;
}
