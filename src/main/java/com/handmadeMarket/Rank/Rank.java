package com.handmadeMarket.Rank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank {
    private String id;
     @Field("rank_number")
    private int rankNumber;
    @Field("rank_name")
    private String rankName;

    private int points;
    @Field("rank_discount")
    private double rankDiscount;
}
