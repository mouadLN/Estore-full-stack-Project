package com.estore.e_strore_backend.inventory.repository;

import com.estore.e_strore_backend.inventory.entity.Inventory;
import com.estore.e_strore_backend.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Find inventory by product
    Optional<Inventory> findByProduct(Product product);

    // Find inventory by product id
    Optional<Inventory> findByProductId(Long productId);

    // Check if inventory exists for a product
    boolean existsByProductId(Long productId);
}