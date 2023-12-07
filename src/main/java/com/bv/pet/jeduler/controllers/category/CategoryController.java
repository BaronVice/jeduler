package com.bv.pet.jeduler.controllers.category;

import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;
import com.bv.pet.jeduler.services.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/category")
public class CategoryController implements ICategoryController {
    private final CategoryService categoryService;

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryDto>> allCategories() {
        return ResponseEntity.ok(categoryService.all());
    }

    @Override
    @PostMapping
    public ResponseEntity<Short> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Short id = categoryService.create(categoryDto);

        return ResponseEntity
                .created(URI.create("/jeduler/category/" + id))
                .body(id);
    }

    @Override
    @PatchMapping
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.update(categoryDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Short id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
