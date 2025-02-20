package com.handmadeMarket.Review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Review {
    private String id;

    @Field("review_rating")
    private int reviewRating;

    @Field("review_comment")
    private String reviewComment;

    @Field("review_created_date")
    private Date reviewCreatedDate;

    @Field("review_updated_date")
    private Date reviewUpdatedDate;

    @Field("product_id")
    private String productId;

}
