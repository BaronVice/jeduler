package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.controllers.interfaces.ICategoryController;
import com.bv.pet.jeduler.dtos.CategoryDto;
import com.bv.pet.jeduler.services.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/category")
public class CategoryController implements ICategoryController {
    private final CategoryService categoryService;

    @Override
    @GetMapping
    public ResponseEntity<TreeSet<CategoryDto>> allCategories() {
        return ResponseEntity.ok(
                new TreeSet<>(categoryService.all())
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.create(categoryDto);

        return ResponseEntity
                .created(URI.create("/jeduler/category/" + createdCategory.getId()))
                .body(createdCategory);
    }

    @Override
    @PatchMapping
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.update(categoryDto);

        return ResponseEntity
                .created(URI.create("/jeduler/category/" + updatedCategory.getId()))
                .body(updatedCategory);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
