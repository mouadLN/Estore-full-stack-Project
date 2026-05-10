package com.estore.e_strore_backend.review.controller;

import com.estore.e_strore_backend.review.dto.ReviewRequest;
import com.estore.e_strore_backend.review.dto.ReviewResponse;
import com.estore.e_strore_backend.review.service.ReviewService;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Create review
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createReview(@PathVariable Long userId,
                                          @RequestBody @Valid ReviewRequest request,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        ReviewResponse response = reviewService.createReview(userId, request);

        if (response == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User or product not found"));
        }

        return ResponseEntity.ok(response);
    }

    // Get reviews by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewResponse> responses = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(responses);
    }

    // Get reviews by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewResponse> responses = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    // Get average rating for a product
    @GetMapping("/product/{productId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        Double average = reviewService.getAverageRating(productId);
        return ResponseEntity.ok(average);
    }

    // Delete review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        boolean deleted = reviewService.deleteReview(reviewId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}