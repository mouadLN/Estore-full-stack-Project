package com.estore.e_strore_backend.shopping.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponse {
    private Long cartId;
    private Long userId;
    private String userEmail;
    private List<CartItemResponse> items = new ArrayList<>();
    private Integer totalItems;
    private Double totalPrice;
}