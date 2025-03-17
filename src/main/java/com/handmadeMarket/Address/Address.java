package com.handmadeMarket.Address;

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
public class Address {
    @Id
    private String id;

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

    @Field("recipient_name")
    private String recipientName;

    @Field("address_of_user_id")
    private String addressOfUserId;

}
