package com.handmadeMarket.Review;

import com.handmadeMarket.Review.dto.ReviewResponseDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByReviewUserId(String userId);
    List<Review> findByReviewProductId(String productId);
}
