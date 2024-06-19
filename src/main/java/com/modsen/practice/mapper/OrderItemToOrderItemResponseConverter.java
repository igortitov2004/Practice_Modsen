package com.modsen.practice.mapper;

import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

public class OrderItemToOrderItemResponseConverter implements Converter<OrderItem, OrderItemResponse> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public OrderItemResponse convert(OrderItem source) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setOrderId(source.getOrder().getId());
        orderItemResponse.setCount(source.getCount());
        orderItemResponse.setId(source.getId());
        orderItemResponse.setProduct(conversionService.convert(source.getProduct(), ProductResponse.class));
        return orderItemResponse;
    }
}
