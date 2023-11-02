package com.bv.pet.jeduler.services.interfaces;

import com.bv.pet.jeduler.dtos.CategoryDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> all();
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto);
    void delete(Long id);
}