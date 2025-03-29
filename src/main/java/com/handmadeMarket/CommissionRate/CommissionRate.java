package com.handmadeMarket.CommissionRate;

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
public class CommissionRate {
    @Id
    private String id;

    @Field("min_price")
    private int minPrice;

    @Field("max_price")
    private int maxPrice;

    @Field("commission_rate")
    private double commissionRate;
}
