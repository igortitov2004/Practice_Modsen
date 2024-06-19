package com.modsen.practice.mapper;

import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ProductRequestListToProductListConverter implements Converter<List<ProductRequest>, List<Product>> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public List<Product> convert(List<ProductRequest> source) {
        List<Product> result = new ArrayList<>();

        for (var productRequest: source) {
            result.add(conversionService.convert(productRequest, Product.class));
        }

        return result;
    }
}
