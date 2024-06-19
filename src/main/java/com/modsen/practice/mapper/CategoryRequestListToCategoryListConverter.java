package com.modsen.practice.mapper;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class CategoryRequestListToCategoryListConverter implements Converter<List<CategoryRequest>, List<Category>> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<Category> convert(List<CategoryRequest> source) {
        List<Category> result = new ArrayList<>();

        for (var categoryRequest: source) {
            result.add(conversionService.convert(categoryRequest, Category.class));
        }

        return result;
    }
}
