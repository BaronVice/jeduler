package com.bv.pet.jeduler.controllers;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.SingleValueResponse;
import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;
import com.bv.pet.jeduler.services.category.CategoryService;
import com.bv.pet.jeduler.services.user.UserService;
import com.bv.pet.jeduler.utils.AllowedAmount;
import com.bv.pet.jeduler.utils.Assert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ApplicationInfo applicationInfo;
    private final Assert anAssert;

    @GetMapping
    public ResponseEntity<?> allCategories(
            Principal principal
    ) {
        short userId = userService.getIdOrElseCreateAndGet(principal.getName());

        return ResponseEntity.ok(
                categoryService.all(userId)
        );
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            Principal principal,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        short userId = userService.getIdOrElseCreateAndGet(principal.getName());
        anAssert.allowedCreation(
                applicationInfo.userInfoCategories().getInfo().get(userId),
                AllowedAmount.CATEGORY
        );

        Short id = categoryService.create(userId, categoryDto);
        return ResponseEntity.ok(new SingleValueResponse<>(id));
    }

    @PatchMapping
    public ResponseEntity<?> updateCategory(
            Principal principal,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        short userId = userService.getIdOrElseCreateAndGet(principal.getName());
        categoryService.update(
                userId,
                categoryDto
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            Principal principal,
            @PathVariable Short id
    ) {
        short userId = userService.getIdOrElseCreateAndGet(principal.getName());
        System.out.println("HEEEEEE");
        categoryService.delete(userId, id);

        return ResponseEntity.ok().build();
    }
}
