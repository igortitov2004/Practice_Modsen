package com.modsen.practice.mapper;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

public class ProductToProductResponseConverter implements Converter<Product, ProductResponse> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public ProductResponse convert(Product source) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(source.getId());
        productResponse.setName(source.getName());
        productResponse.setPrice(source.getPrice());
        productResponse.setCategory(conversionService.convert(source.getCategory(), CategoryResponse.class));
        productResponse.setIngredients(source.getIngredients());
        productResponse.setDescription(source.getDescription());
        productResponse.setWeight(source.getWeight());
        productResponse.setCaloric_value(source.getCaloricValue());

        return productResponse;
    }
}
