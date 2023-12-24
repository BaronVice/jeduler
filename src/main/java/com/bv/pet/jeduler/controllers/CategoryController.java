package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.category.ICategoryService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/category")
public class CategoryController {
    private final ICategoryService categoryService;
    private final ApplicationInfo applicationInfo;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> allCategories(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                categoryService.all(userDetails.getUserId())
        );
    }

    @PostMapping
    public ResponseEntity<Short> createCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        short userId = userDetails.getUserId();
        Assert.assertAllowedAmount(
                applicationInfo.userInfoCategories().getInfo().get(userId),
                AllowedAmount.CATEGORY
        );

        Short id = categoryService.create(userId, categoryDto);

        return ResponseEntity
                .created(URI.create("/jeduler/category/" + id))
                .body(id);
    }

    @PatchMapping
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.update(categoryDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Short id
    ) {
        categoryService.delete(userDetails.getUserId(), id);
        return ResponseEntity.ok().build();
    }
}
