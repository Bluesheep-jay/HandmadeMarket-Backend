package com.handmadeMarket.ShopRevenue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyShopRevenue {
    @Field("_id")
    private String month;

    private double totalRevenue;
    private double commissionRate;
    private double commissionFee;
}
