package com.estore.e_strore_backend.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockResponse {
    private boolean inStock;
    private Integer quantity;
}