package com.estore.e_strore_backend.catalog.controller;

import com.estore.e_strore_backend.catalog.dto.ProductRequest;
import com.estore.e_strore_backend.catalog.dto.ProductResponse;
import com.estore.e_strore_backend.catalog.entity.Product;
import com.estore.e_strore_backend.catalog.repository.ProductRepository;
import com.estore.e_strore_backend.catalog.service.ProductService;
import com.estore.e_strore_backend.inventory.entity.Inventory;
import com.estore.e_strore_backend.inventory.repository.InventoryRepository;
import com.estore.e_strore_backend.review.entity.Review;
import com.estore.e_strore_backend.review.repository.ReviewRepository;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequest request,
                                           BindingResult bindingResult) {
        // Check validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        ProductResponse response = productService.createProduct(request);
        if (response == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Category not found"));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = productService.getAllProducts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductResponse> responses = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        List<ProductResponse> responses = productService.searchProducts(keyword);
        return ResponseEntity.ok(responses);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody @Valid ProductRequest request,
                                           BindingResult bindingResult) {
        // Check validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        ProductResponse response = productService.updateProduct(id, request);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}