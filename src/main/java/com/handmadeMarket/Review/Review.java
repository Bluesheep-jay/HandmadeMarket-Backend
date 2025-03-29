package com.handmadeMarket.Review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

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

    @Field("is_review_edited")
    private boolean isReviewEdited = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Field("review_created_date")
    private Instant reviewCreatedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Field("review_updated_date")
    private Instant reviewUpdatedDate;

    @Field("review_user_id")
    private String reviewUserId;

    @Field("review_product_id")
    private String reviewProductId;
}
