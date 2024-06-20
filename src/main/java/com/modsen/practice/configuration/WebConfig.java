package com.modsen.practice.configuration;

import com.modsen.practice.mapper.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderRequestToOrderConverter());
        registry.addConverter(new OrderToOrderResponseConverter());
        registry.addConverter(new OrderItemToOrderItemResponseConverter());
        registry.addConverter(new OrderItemRequestToOrderItemConverter());
        registry.addConverter(new UserRequestToUserConverter());
        registry.addConverter(new UserToUserResponseConverter());
        registry.addConverter(new ProductRequestToProductConverter());
        registry.addConverter(new ProductToProductResponseConverter());
        registry.addConverter(new ProductRequestListToProductListConverter());
        registry.addConverter(new ProductListToProductResponseListConverter());
        registry.addConverter(new CategoryRequestToCategoryConverter());
        registry.addConverter(new CategoryToCategoryResponseConverter());
        registry.addConverter(new CategoryRequestListToCategoryListConverter());
        registry.addConverter(new CategoryListToCategoryResponseListConverter());
    }
}

