package com.example.demo.restaurant.mapper;

import org.mapstruct.*;
import com.example.demo.restaurant.dto.CategoryDto;
import com.example.demo.restaurant.dto.request.CreateCategoryRequest;
import com.example.demo.restaurant.entity.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ItemMapper.class})
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    CategoryDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);
}