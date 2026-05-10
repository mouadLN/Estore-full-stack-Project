package com.estore.e_strore_backend.catalog.repository;

import com.estore.e_strore_backend.catalog.entity.Product;
import com.estore.e_strore_backend.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by category
    List<Product> findByCategory(Category category);

    // Find products by category id
    List<Product> findByCategoryId(Long categoryId);

    // Search products by name (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Search by name or description
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);
}