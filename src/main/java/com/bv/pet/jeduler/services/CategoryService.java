package com.bv.pet.jeduler.services;

import com.bv.pet.jeduler.dtos.CategoryDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.CategoryMapper;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> all() {
        return categoryMapper.toCategoryDtoList(
                categoryRepository.findAll()
        );
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        categoryRepository.save(category);

        return categoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        Category toUpdate = categoryRepository.findById(categoryDto.getId()).orElseThrow(
                () -> new ApplicationException("Category not found", HttpStatus.NOT_FOUND)
        );

        Category category = categoryMapper.toCategory(categoryDto);
        toUpdate.setName(category.getName());
        toUpdate.setColor(category.getColor());

        categoryRepository.save(toUpdate);

        return categoryMapper.toCategoryDto(toUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
