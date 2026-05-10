package com.estore.e_strore_backend.inventory.service;

import com.estore.e_strore_backend.catalog.entity.Product;
import com.estore.e_strore_backend.catalog.repository.ProductRepository;
import com.estore.e_strore_backend.inventory.dto.InventoryRequest;
import com.estore.e_strore_backend.inventory.dto.InventoryResponse;
import com.estore.e_strore_backend.inventory.entity.Inventory;
import com.estore.e_strore_backend.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    // Create or update inventory for a product
    public InventoryResponse createOrUpdateInventory(InventoryRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElse(null);

        if (product == null) {
            return null;
        }

        Inventory inventory = inventoryRepository.findByProductId(request.getProductId()).orElse(null);

        if (inventory == null) {
            // Create new inventory
            inventory = new Inventory();
            inventory.setProduct(product);
        }

        inventory.setQuantity(request.getQuantity());

        Inventory saved = inventoryRepository.save(inventory);
        return convertToResponse(saved);
    }

    // Get inventory by product id
    public InventoryResponse getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);

        if (inventory == null) {
            return null;
        }

        return convertToResponse(inventory);
    }

    // Get all inventory
    public List<InventoryResponse> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        List<InventoryResponse> responses = new ArrayList<>();

        for (Inventory inventory : inventoryList) {
            responses.add(convertToResponse(inventory));
        }

        return responses;
    }

    // Check if product is in stock (quantity > 0)
    public boolean isInStock(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);

        if (inventory == null) {
            return false;
        }

        return inventory.getQuantity() > 0;
    }

    // Get current quantity
    public Integer getQuantity(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);

        if (inventory == null) {
            return 0;
        }

        return inventory.getQuantity();
    }

    // Update quantity (can be positive or negative)
    public InventoryResponse updateQuantity(Long productId, Integer change) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);

        if (inventory == null) {
            return null;
        }

        int newQuantity = inventory.getQuantity() + change;

        if (newQuantity < 0) {
            return null; // Not enough stock
        }

        inventory.setQuantity(newQuantity);
        Inventory saved = inventoryRepository.save(inventory);
        return convertToResponse(saved);
    }

    // Delete inventory
    public boolean deleteInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);

        if (inventory == null) {
            return false;
        }

        inventoryRepository.delete(inventory);
        return true;
    }

    // Convert entity to response DTO
    private InventoryResponse convertToResponse(Inventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setQuantity(inventory.getQuantity());

        if (inventory.getProduct() != null) {
            response.setProductId(inventory.getProduct().getId());
            response.setProductName(inventory.getProduct().getName());
        }

        return response;
    }
}