package com.bv.pet.jeduler.services.category;

import com.bv.pet.jeduler.dtos.CategoryDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.CategoryMapper;
import com.bv.pet.jeduler.repositories.CategoryRepository;
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
                categoryRepository.findByOrderByNameAsc()
        );
    }

    @Override
    @Transactional
    public Short create(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        categoryRepository.save(category);

        return category.getId();
    }

    @Override
    @Transactional
    public void update(CategoryDto categoryDto) {
        Category toUpdate = categoryRepository.findById(categoryDto.getId()).orElseThrow(
                () -> new ApplicationException("Category not found", HttpStatus.NOT_FOUND)
        );

        Category category = categoryMapper.toCategory(categoryDto);
        toUpdate.setName(category.getName());
        toUpdate.setColor(category.getColor());

        categoryRepository.save(toUpdate);
    }

    @Override
    @Transactional
    public void delete(Short id) {
        categoryRepository.deleteById(id);
    }
}
