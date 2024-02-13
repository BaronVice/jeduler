package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.CreateResponse;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/category")
public class CategoryController {
    private final ICategoryService categoryService;
    private final ApplicationInfo applicationInfo;
    private final Assert anAssert;

    @GetMapping
    public ResponseEntity<?> allCategories(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(
                categoryService.all(userDetails.getUserId())
        );
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        short userId = userDetails.getUserId();
        anAssert.allowedCreation(
                applicationInfo.userInfoCategories().getInfo().get(userId),
                AllowedAmount.CATEGORY
        );

        Short id = categoryService.create(userId, categoryDto);
        return ResponseEntity.ok(new CreateResponse<>(id));
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
