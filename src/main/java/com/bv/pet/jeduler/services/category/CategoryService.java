package com.bv.pet.jeduler.services.category;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.CategoryMapper;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ApplicationInfo applicationInfo;

    @Transactional(readOnly = true)
    public List<CategoryDto> all(short userId) {
        return categoryMapper.toCategoryDtoList(
                categoryRepository.findByUserIdOrderByNameAsc(userId)
        );
    }

    @Transactional
    public Short create(short userId, CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        category.setUser(User.builder().id(userId).build());

        categoryRepository.save(category);
        applicationInfo.userInfoCategories().changeValue(userId, (short) 1);

        return category.getId();
    }

    @Transactional
    public void update(
            short userId,
            CategoryDto categoryDto
    ) {
        Category toUpdate = categoryRepository.findByUserIdAndId(userId, categoryDto.id()).orElseThrow(
                () -> new ApplicationException("Category not found", HttpStatus.NOT_FOUND)
        );

        Category category = categoryMapper.toCategory(categoryDto);
        toUpdate.setName(category.getName());
        toUpdate.setColor(category.getColor());

        categoryRepository.save(toUpdate);
    }

    @Transactional
    public void delete(short userId, short id) {
        Optional<Category> category = categoryRepository.findByUserIdAndId(userId, id);
        System.out.println(category.isPresent());
        if (category.isPresent()){
            categoryRepository.deleteById(id);
            applicationInfo.userInfoCategories().changeValue(userId, (short) -1);
        }
    }
}
