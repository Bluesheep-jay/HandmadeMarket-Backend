package com.handmadeMarket.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyRevenueResult {
    @Field("_id")
    private String day;
    private double totalRevenue;
}