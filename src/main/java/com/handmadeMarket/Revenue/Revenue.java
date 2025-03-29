package com.handmadeMarket.Revenue;

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
public class Revenue {
    @Id
    private String id;

    @Field("shop_id")
    private String shopId;

    @Field("year")
    private int year;

    @Field("month")
    private int month;

    @Field("total_revenue")
    private double totalRevenue;

    @Field("commission_rate")
    private double commissionRate;
}
