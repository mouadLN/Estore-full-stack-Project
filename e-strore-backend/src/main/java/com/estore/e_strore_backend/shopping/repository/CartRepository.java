package com.estore.e_strore_backend.shopping.repository;

import com.estore.e_strore_backend.shopping.entity.Cart;
import com.estore.e_strore_backend.customer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find cart by user
    Optional<Cart> findByUser(User user);

    // Find cart by user id
    Optional<Cart> findByUserId(Long userId);

    // Check if cart exists for user
    boolean existsByUserId(Long userId);
}