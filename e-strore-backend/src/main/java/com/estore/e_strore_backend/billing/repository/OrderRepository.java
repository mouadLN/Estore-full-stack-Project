package com.estore.e_strore_backend.billing.repository;

import com.estore.e_strore_backend.billing.entity.Order;
import com.estore.e_strore_backend.customer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by user
    List<Order> findByUser(User user);

    // Find orders by user id
    List<Order> findByUserId(Long userId);

    // Find orders by status
    List<Order> findByStatus(String status);
}