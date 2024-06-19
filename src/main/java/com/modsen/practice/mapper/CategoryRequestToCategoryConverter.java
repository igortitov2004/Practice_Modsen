package com.modsen.practice.mapper;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.entity.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryRequestToCategoryConverter implements Converter<CategoryRequest, Category> {
    @Override
    public Category convert(CategoryRequest source) {
        Category category = new Category();

        category.setId(source.getId());
        category.setName(source.getName());

        return category;
    }
}
