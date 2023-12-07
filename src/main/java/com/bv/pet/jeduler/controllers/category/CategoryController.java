package com.bv.pet.jeduler.controllers.category;

import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;
import com.bv.pet.jeduler.services.authentication.userdetails.UserDetailsImpl;
import com.bv.pet.jeduler.services.category.CategoryService;
import com.bv.pet.jeduler.utils.Assert;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/category")
public class CategoryController implements ICategoryController {
    private final CategoryService categoryService;
    private final List<Short> categoryPerUser;

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryDto>> allCategories(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return ResponseEntity.ok(categoryService.all());
    }

    @Override
    @PostMapping
    public ResponseEntity<Short> createCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        short userId = userDetails.getUserId();
        Assert.assertAllowedCategoryAmount(categoryPerUser.get(userId));

        Short id = categoryService.create(categoryDto);
        changeCategoryPerUser(userId, 1);

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
// TODO       changeCategoryPerUser();
        return ResponseEntity.ok().build();
    }

    private void changeCategoryPerUser(short userId, int value) {
        categoryPerUser.set(userId, (short) (categoryPerUser.get(userId) + value));
    }
}
