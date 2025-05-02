package com.handmadeMarket.Platform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformRevenue {
    @Field("_id")
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
