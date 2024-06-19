package com.modsen.practice.mapper;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class CategoryListToCategoryResponseListConverter implements Converter<List<Category>, List<CategoryResponse>> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<CategoryResponse> convert(List<Category> source) {
        List<CategoryResponse> result = new ArrayList<>();

        for (var category: source) {
            result.add(conversionService.convert(category, CategoryResponse.class));
        }

        return result;
    }
}
