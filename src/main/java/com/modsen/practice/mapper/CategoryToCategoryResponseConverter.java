package com.modsen.practice.mapper;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.entity.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryToCategoryResponseConverter implements Converter<Category, CategoryResponse> {
    @Override
    public CategoryResponse convert(Category source) {
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(source.getId());
        categoryResponse.setName(source.getName());

        return categoryResponse;
    }
}
