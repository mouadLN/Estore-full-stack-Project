package com.estore.e_strore_backend.billing.service;

import com.estore.e_strore_backend.billing.dto.OrderItemResponse;
import com.estore.e_strore_backend.billing.dto.OrderResponse;
import com.estore.e_strore_backend.billing.entity.Order;
import com.estore.e_strore_backend.billing.entity.OrderItem;
import com.estore.e_strore_backend.billing.repository.OrderItemRepository;
import com.estore.e_strore_backend.billing.repository.OrderRepository;
import com.estore.e_strore_backend.customer.entity.User;
import com.estore.e_strore_backend.customer.repository.UserRepository;
import com.estore.e_strore_backend.inventory.repository.InventoryRepository;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final InventoryRepository inventoryRepository;

    // Create order from cart
    @Transactional
    public OrderResponse createOrderFromCart(Long userId) {
        // Get user
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        // Get cart
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null || cart.getItems().isEmpty()) {
            return null;
        }

        // Check stock availability
        for (CartItem cartItem : cart.getItems()) {
            Integer availableStock = inventoryRepository.findByProductId(cartItem.getProduct().getId())
                    .map(inv -> inv.getQuantity())
                    .orElse(0);

            if (availableStock < cartItem.getQuantity()) {
                return null; // Not enough stock
            }
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus("CONFIRMED");

        double totalAmount = 0;

        // Create order items from cart items
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());

            double subtotal = cartItem.getPrice() * cartItem.getQuantity();
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            totalAmount += subtotal;

            // Update inventory (decrease stock)
            inventoryRepository.findByProductId(cartItem.getProduct().getId())
                    .ifPresent(inventory -> {
                        int newQuantity = inventory.getQuantity() - cartItem.getQuantity();
                        inventory.setQuantity(newQuantity);
                        inventoryRepository.save(inventory);
                    });
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Save order items
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        // Clear cart
        cartItemRepository.deleteByCart(cart);
        cart.getItems().clear();
        cartRepository.save(cart);

        return convertToResponse(savedOrder);
    }

    // Get order by id (read only - no transaction needed)
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        return convertToResponse(order);
    }

    // Get all orders for a user (read only)
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> responses = new ArrayList<>();

        for (Order order : orders) {
            responses.add(convertToResponse(order));
        }

        return responses;
    }

    // Get all orders (admin) (read only)
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responses = new ArrayList<>();

        for (Order order : orders) {
            responses.add(convertToResponse(order));
        }

        return responses;
    }

    // Update order status
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        order.setStatus(status);
        Order updated = orderRepository.save(order);
        return convertToResponse(updated);
    }

    // Convert entity to response DTO
    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());

        if (order.getUser() != null) {
            response.setUserId(order.getUser().getId());
            response.setUserEmail(order.getUser().getEmail());
            response.setUserFirstName(order.getUser().getFirstName());
            response.setUserLastName(order.getUser().getLastName());
        }

        List<OrderItemResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setSubtotal(item.getSubtotal());

            if (item.getProduct() != null) {
                itemResponse.setProductId(item.getProduct().getId());
                itemResponse.setProductName(item.getProduct().getName());
            }

            itemResponses.add(itemResponse);
        }

        response.setItems(itemResponses);
        return response;
    }
}