package com.estore.e_strore_backend.review.repository;

import com.estore.e_strore_backend.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);

    List<Review> findByUserId(Long userId);

    List<Review> findByRating(Integer rating);

    void deleteByProductId(Long productId);
}