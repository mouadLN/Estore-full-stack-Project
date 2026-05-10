package com.estore.e_strore_backend.shopping.controller;

import com.estore.e_strore_backend.shopping.dto.CartItemRequest;
import com.estore.e_strore_backend.shopping.dto.CartResponse;
import com.estore.e_strore_backend.shopping.service.CartService;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Long userId) {
        System.out.println("=== GET CART CALLED with userId: " + userId);

        CartResponse response = cartService.getCartResponse(userId);

        System.out.println("=== Response: " + response);

        if (response == null) {
            System.out.println("=== Returning 404");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<?> addToCart(@PathVariable Long userId,
                                       @RequestBody @Valid CartItemRequest request,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        CartResponse response = cartService.addToCart(userId, request);

        if (response == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User or product not found"));
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/{userId}/item/{itemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long userId,
                                            @PathVariable Long itemId,
                                            @RequestParam Integer quantity) {
        if (quantity < 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Quantity cannot be negative"));
        }

        CartResponse response = cartService.updateCartItem(userId, itemId, quantity);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}/item/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long userId,
                                            @PathVariable Long itemId) {
        CartResponse response = cartService.removeFromCart(userId, itemId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().body(new ErrorResponse("Cart cleared successfully"));
    }
}