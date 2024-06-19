package com.modsen.practice.mapper;

import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ProductListToProductResponseListConverter implements Converter<List<Product>, List<ProductResponse>> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<ProductResponse> convert(List<Product> source) {
        List<ProductResponse> result = new ArrayList<>();

        for (var product: source) {
            result.add(conversionService.convert(product, ProductResponse.class));
        }

        return result;
    }
}
