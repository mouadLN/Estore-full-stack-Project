package com.estore.e_strore_backend.review.service;

import com.estore.e_strore_backend.review.dto.ReviewRequest;
import com.estore.e_strore_backend.review.dto.ReviewResponse;
import com.estore.e_strore_backend.review.entity.Review;
import com.estore.e_strore_backend.review.repository.ReviewRepository;
import com.estore.e_strore_backend.customer.entity.User;
import com.estore.e_strore_backend.customer.repository.UserRepository;
import com.estore.e_strore_backend.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // Create review
    public ReviewResponse createReview(Long userId, ReviewRequest request) {
        // Check if user exists
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        // Check if product exists
        if (!productRepository.existsById(request.getProductId())) {
            return null;
        }

        // Create review
        Review review = Review.builder()
                .productId(request.getProductId())
                .userId(userId)
                .userEmail(user.getEmail())
                .userName(user.getFirstName() + " " + user.getLastName())
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        return convertToResponse(saved);
    }

    // Get reviews by product ID
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviews) {
            responses.add(convertToResponse(review));
        }

        return responses;
    }

    // Get reviews by user ID
    public List<ReviewResponse> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviews) {
            responses.add(convertToResponse(review));
        }

        return responses;
    }

    // Get average rating for a product
    public Double getAverageRating(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }

        return sum / reviews.size();
    }

    // Delete review - FIXED: Long instead of String
    public boolean deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            return false;
        }
        reviewRepository.deleteById(reviewId);
        return true;
    }

    // Convert entity to response DTO
    private ReviewResponse convertToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setProductId(review.getProductId());
        response.setUserId(review.getUserId());
        response.setUserEmail(review.getUserEmail());
        response.setUserName(review.getUserName());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCreatedAt(review.getCreatedAt());
        return response;
    }
}