package com.bv.pet.jeduler.mappers;

import com.bv.pet.jeduler.dtos.CategoryDto;
import com.bv.pet.jeduler.entities.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    Category toCategory(CategoryDto categoryDto);
    List<CategoryDto> toCategoryDtoList(List<Category> categoryList);
}
