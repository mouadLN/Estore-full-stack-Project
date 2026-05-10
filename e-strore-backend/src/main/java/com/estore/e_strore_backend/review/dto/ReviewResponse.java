package com.estore.e_strore_backend.review.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Long id;  // Changed from String to Long
    private Long productId;
    private Long userId;
    private String userEmail;
    private String userName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}