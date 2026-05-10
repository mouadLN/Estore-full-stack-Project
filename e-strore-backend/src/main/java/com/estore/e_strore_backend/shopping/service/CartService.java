package com.estore.e_strore_backend.shopping.service;

import com.estore.e_strore_backend.catalog.entity.Product;
import com.estore.e_strore_backend.catalog.repository.ProductRepository;
import com.estore.e_strore_backend.customer.entity.User;
import com.estore.e_strore_backend.customer.repository.UserRepository;
import com.estore.e_strore_backend.shopping.dto.CartItemRequest;
import com.estore.e_strore_backend.shopping.dto.CartItemResponse;
import com.estore.e_strore_backend.shopping.dto.CartResponse;
import com.estore.e_strore_backend.shopping.entity.Cart;
import com.estore.e_strore_backend.shopping.entity.CartItem;
import com.estore.e_strore_backend.shopping.repository.CartItemRepository;
import com.estore.e_strore_backend.shopping.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // Get or create cart for user
    private Cart getOrCreateCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return null;
            }

            cart = new Cart();
            cart.setUser(user);
            cart.setItems(new ArrayList<>());
            cart = cartRepository.save(cart);
        }

        return cart;
    }

    // Add item to cart
    @Transactional
    public CartResponse addToCart(Long userId, CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);
        if (cart == null) {
            return null;
        }

        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return null;
        }

        // Check if product already in cart
        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (existingItem != null) {
            // Update quantity
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // New item
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(product.getPrice());
            cartItemRepository.save(newItem);

            cart.getItems().add(newItem);
        }

        return getCartResponse(userId);
    }

    // Get cart (read only - no transaction needed)
    public CartResponse getCartResponse(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);

        if (cart == null) {
            return null;
        }

        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        response.setUserId(userId);

        if (cart.getUser() != null) {
            response.setUserEmail(cart.getUser().getEmail());
        }

        List<CartItemResponse> itemResponses = new ArrayList<>();
        double totalPrice = 0;
        int totalItems = 0;

        for (CartItem item : cart.getItems()) {
            CartItemResponse itemResponse = new CartItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setProductId(item.getProduct().getId());
            itemResponse.setProductName(item.getProduct().getName());
            itemResponse.setProductPrice(item.getPrice());
            itemResponse.setQuantity(item.getQuantity());

            double subtotal = item.getPrice() * item.getQuantity();
            itemResponse.setSubtotal(subtotal);

            itemResponses.add(itemResponse);
            totalPrice += subtotal;
            totalItems += item.getQuantity();
        }

        response.setItems(itemResponses);
        response.setTotalItems(totalItems);
        response.setTotalPrice(totalPrice);

        return response;
    }

    // Update cart item quantity
    @Transactional
    public CartResponse updateCartItem(Long userId, Long itemId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            return null;
        }

        CartItem item = cartItemRepository.findById(itemId).orElse(null);
        if (item == null || !item.getCart().getId().equals(cart.getId())) {
            return null;
        }

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return getCartResponse(userId);
    }

    // Remove item from cart
    @Transactional
    public CartResponse removeFromCart(Long userId, Long itemId) {
        return updateCartItem(userId, itemId, 0);
    }

    // Clear cart
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart != null) {
            cartItemRepository.deleteByCart(cart);
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }
}