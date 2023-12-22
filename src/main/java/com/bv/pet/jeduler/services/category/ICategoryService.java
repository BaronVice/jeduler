package com.bv.pet.jeduler.services.category;

import com.bv.pet.jeduler.datacarriers.dtos.CategoryDto;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> all(short userId);
    Short create(CategoryDto categoryDto);
    void update(CategoryDto categoryDto);
    void delete(Short id);
}
