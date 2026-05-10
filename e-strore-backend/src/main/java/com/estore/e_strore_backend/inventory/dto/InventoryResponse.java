package com.estore.e_strore_backend.inventory.dto;

import lombok.Data;

@Data
public class InventoryResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
}