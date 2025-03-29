package com.handmadeMarket.Review;

import com.handmadeMarket.Review.dto.ReviewResponseDTO;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/by-user/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable String userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/by-product/{productId}")
    public List<ReviewResponseDTO> getReviewsByProductId(@PathVariable String productId) {
        return reviewService.getReviewsByProductId(productId);
    }
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }



    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }
}
