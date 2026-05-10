package com.estore.e_strore_backend.catalog.service;

import com.estore.e_strore_backend.catalog.dto.CategoryRequest;
import com.estore.e_strore_backend.catalog.dto.CategoryResponse;
import com.estore.e_strore_backend.catalog.entity.Category;
import com.estore.e_strore_backend.catalog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Create category
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category saved = categoryRepository.save(category);
        return convertToResponse(saved);
    }

    // Get all categories
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = new ArrayList<>();

        for (Category category : categories) {
            responses.add(convertToResponse(category));
        }

        return responses;
    }

    // Get category by id
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            return null;
        }

        return convertToResponse(category);
    }

    // Update category
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            return null;
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updated = categoryRepository.save(category);
        return convertToResponse(updated);
    }

    // Delete category
    public boolean deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }

    // Convert entity to response DTO
    private CategoryResponse convertToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }

}