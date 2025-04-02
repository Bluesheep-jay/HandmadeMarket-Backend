package com.handmadeMarket;

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
public class PlatformRevenue {
    @Id
    private String id;

    @Field("month")
    private String month;

    @Field("total_platform_revenue")
    private double totalPlatformRevenue;
}
