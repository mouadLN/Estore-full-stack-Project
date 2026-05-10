package com.estore.e_strore_backend.inventory.controller;

import com.estore.e_strore_backend.inventory.dto.InventoryRequest;
import com.estore.e_strore_backend.inventory.dto.InventoryResponse;
import com.estore.e_strore_backend.inventory.dto.StockResponse;
import com.estore.e_strore_backend.inventory.service.InventoryService;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Create or update inventory
    @PostMapping
    public ResponseEntity<?> createOrUpdateInventory(@RequestBody @Valid InventoryRequest request,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        InventoryResponse response = inventoryService.createOrUpdateInventory(request);

        if (response == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Product not found"));
        }

        return ResponseEntity.ok(response);
    }

    // Get inventory by product id
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getInventoryByProductId(@PathVariable Long productId) {
        InventoryResponse response = inventoryService.getInventoryByProductId(productId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // Get all inventory
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    // Check if product is in stock
    @GetMapping("/product/{productId}/instock")
    public ResponseEntity<?> isInStock(@PathVariable Long productId) {
        boolean inStock = inventoryService.isInStock(productId);
        return ResponseEntity.ok(new StockResponse(inStock, inventoryService.getQuantity(productId)));
    }

    // Update quantity (add or remove)
    @PutMapping("/product/{productId}/quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long productId,
                                            @RequestParam Integer change) {
        InventoryResponse response = inventoryService.updateQuantity(productId, change);

        if (response == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Not enough stock or inventory not found"));
        }

        return ResponseEntity.ok(response);
    }

    // Delete inventory
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId) {
        boolean deleted = inventoryService.deleteInventory(productId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}