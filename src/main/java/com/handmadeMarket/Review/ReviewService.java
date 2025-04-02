package com.handmadeMarket.Review;

import com.handmadeMarket.Product.ProductService;
import com.handmadeMarket.Review.dto.ReviewResponseDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private MongoTemplate mongoTemplate;
    private ProductService productService;

    public ReviewService(ReviewRepository reviewRepository, MongoTemplate mongoTemplate, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.mongoTemplate = mongoTemplate;
        this.productService = productService;
    }

    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByReviewUserId(userId);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review addReview(Review review) {
        review.setReviewEdited(true);
        List<Review> reviews = reviewRepository.findByReviewProductId(review.getReviewProductId());
        productService.updateRating(review.getReviewProductId(), review.getReviewRating(), reviews);
        return reviewRepository.save(review);
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewResponseDTO> getReviewsByProductId(String productId) {
        Aggregation aggregation = Aggregation.newAggregation(
                // Lọc review theo productId
                Aggregation.match(Criteria.where("review_product_id").is(productId)),

                Aggregation.addFields().addField("review.review_user_id")
                        .withValue(ConvertOperators.ToObjectId.toObjectId("$review_user_id"))
                        .build(),

                Aggregation.lookup("users", "review.review_user_id", "_id", "userInfo"),

                // Giữ kết quả ngay cả khi không có user
                Aggregation.unwind("userInfo", true),

                Aggregation.addFields().addField("userInfo.username").withValue("$userInfo.username")
                        .addField("userInfo.avatarUrl").withValue("$userInfo.avatar_url")
                        .build(),
                // Project để chọn các trường mong muốn
                Aggregation.project()
                        .andExpression("$_id").as("id")
                        .andExpression("$review_rating").as("reviewRating")
                        .andExpression("$review_comment").as("reviewComment")
                        .andExpression("$is_review_edited").as("reviewEdited")
                        .andExpression("$review_created_date").as("reviewCreatedDate")
                        .andExpression("$review_updated_date").as("reviewUpdatedDate")
                        .andExpression("$review_user_id").as("reviewUserId")
                        .andExpression("$review_product_id").as("reviewProductId")
                        .andExpression("$userInfo.username").as("username")
                        .andExpression("$userInfo.avatar_url").as("avatarUrl")
//
//                Aggregation.group("_id")
//                        .first("review_rating").as("reviewRating")
//                        .first("review_comment").as("reviewComment")
//                        .first("is_review_edited").as("reviewEdited")
//                        .first("review_created_date").as("reviewCreatedDate")
//                        .first("review_updated_date").as("reviewUpdatedDate")
//                        .first("review_user_id").as("reviewUserId")
//                        .first("review_product_id").as("reviewProductId")
//                        .push("order_details").as("orderDetails")

        );

        return mongoTemplate.aggregate(aggregation, "review", ReviewResponseDTO.class).getMappedResults();
    }
}
