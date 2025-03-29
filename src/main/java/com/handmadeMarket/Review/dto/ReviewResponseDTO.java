package com.handmadeMarket.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {

    private String id;
    private int reviewRating;
    private String reviewComment;
    private boolean isReviewEdited;
    private Instant reviewCreatedDate;
    private Instant reviewUpdatedDate;
    private String reviewUserId;
    private String reviewProductId;

    // Thêm thông tin từ Users
    private String username;
    private String avatarUrl;
}
