package com.handmadeMarket.ShopRevenue;

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
public class ShopRevenue {
    @Id
    private String id;

    @Field("shop_id")
    private String shopId;

    @Field("month")
    private String month;

    @Field("total_revenue")
    private double totalRevenue;

    @Field("commission_rate")
    private double commissionRate;

    @Field("commission_fee")
    private double commissionFee;
}
