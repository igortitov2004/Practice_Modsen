package com.modsen.practice.mapper;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductToProductResponseConverter implements Converter<Product, ProductResponse> {

    private final CategoryToCategoryResponseConverter categoryResponseConverter;

    @Override
    public ProductResponse convert(Product source) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(source.getId());
        productResponse.setName(source.getName());
        productResponse.setPrice(source.getPrice());
        productResponse.setCategory(categoryResponseConverter.convert(source.getCategory()));
        productResponse.setIngredients(source.getIngredients());
        productResponse.setDescription(source.getDescription());
        productResponse.setWeight(source.getWeight());
        productResponse.setCaloricValue(source.getCaloricValue());

        return productResponse;
    }
}
