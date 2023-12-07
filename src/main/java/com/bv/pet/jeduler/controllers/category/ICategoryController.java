package com.bv.pet.jeduler.controllers.category;

import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryController {
    ResponseEntity<List<CategoryDto>> allCategories(UserDetailsImpl userDetails);
    ResponseEntity<Short> createCategory(UserDetailsImpl userDetails, CategoryDto categoryDto);
    ResponseEntity<?> updateCategory(CategoryDto categoryDto);
    ResponseEntity<?> deleteCategory(Short id);
}
