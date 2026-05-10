package com.estore.e_strore_backend.catalog.service;

import com.estore.e_strore_backend.catalog.dto.ProductRequest;
import com.estore.e_strore_backend.catalog.dto.ProductResponse;
import com.estore.e_strore_backend.catalog.entity.Category;
import com.estore.e_strore_backend.catalog.entity.Product;
import com.estore.e_strore_backend.catalog.repository.CategoryRepository;
import com.estore.e_strore_backend.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Create product
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if (category == null) {
            return null;
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return convertToResponse(saved);
    }

    // Get all products
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {
            responses.add(convertToResponse(product));
        }

        return responses;
    }

    // Get product by id
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        return convertToResponse(product);
    }

    // Get products by category
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {
            responses.add(convertToResponse(product));
        }

        return responses;
    }

    // Search products by keyword
    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> products = productRepository.searchByKeyword(keyword);
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {
            responses.add(convertToResponse(product));
        }

        return responses;
    }

    // Update product
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if (category == null) {
            return null;
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return convertToResponse(updated);
    }

    // Delete product
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

    // Convert entity to response DTO
    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }

        return response;
    }
}