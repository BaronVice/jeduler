package com.bv.pet.jeduler.controllers.interfaces;

import com.bv.pet.jeduler.dtos.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.TreeSet;

public interface ICategoryController {
    ResponseEntity<TreeSet<CategoryDto>> allCategories();
    ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto);
    ResponseEntity<CategoryDto> updateCategory(CategoryDto categoryDto);
    ResponseEntity<?> deleteCategory(Long id);
}
