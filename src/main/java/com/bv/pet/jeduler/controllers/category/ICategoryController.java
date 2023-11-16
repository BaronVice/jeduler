package com.bv.pet.jeduler.controllers.category;

import com.bv.pet.jeduler.dtos.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryController {
    ResponseEntity<List<CategoryDto>> allCategories();
    ResponseEntity<Short> createCategory(CategoryDto categoryDto);
    ResponseEntity<?> updateCategory(CategoryDto categoryDto);
    ResponseEntity<?> deleteCategory(Short id);
}
