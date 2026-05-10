package com.estore.e_strore_backend.billing.controller;

import com.estore.e_strore_backend.billing.dto.OrderResponse;
import com.estore.e_strore_backend.billing.service.OrderService;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Create order from cart
    @PostMapping("/user/{userId}/checkout")
    public ResponseEntity<?> createOrderFromCart(@PathVariable Long userId) {
        OrderResponse response = orderService.createOrderFromCart(userId);

        if (response == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Cannot create order. Cart is empty or insufficient stock"));
        }

        return ResponseEntity.ok(response);
    }

    // Get order by id
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        OrderResponse response = orderService.getOrderById(orderId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // Get all orders for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponse> responses = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    // Get all orders (admin)
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> responses = orderService.getAllOrders();
        return ResponseEntity.ok(responses);
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                               @RequestParam String status) {
        OrderResponse response = orderService.updateOrderStatus(orderId, status);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}