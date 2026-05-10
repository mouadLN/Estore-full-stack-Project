package com.estore.e_strore_backend.shopping.repository;

import com.estore.e_strore_backend.shopping.entity.Cart;
import com.estore.e_strore_backend.shopping.entity.CartItem;
import com.estore.e_strore_backend.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find cart item by cart and product
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    // Delete all items in a cart
    void deleteByCart(Cart cart);
}