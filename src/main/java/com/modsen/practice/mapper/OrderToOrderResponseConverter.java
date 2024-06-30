package com.modsen.practice.mapper;

import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {

    private final OrderItemToOrderItemResponseConverter orderItemResponseConverter;

    @Override
    public OrderResponse convert(Order source) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(source.getId());
        orderResponse.setUserId(source.getUser().getId());
        orderResponse.setPrice(source.getPrice());
        orderResponse.setStatus(source.getStatus().toString());
        orderResponse.setCity(source.getCity());
        orderResponse.setStreet(source.getStreet());
        orderResponse.setHouseNumber(source.getHouseNumber());
        orderResponse.setApartmentNumber(source.getApartmentNumber());
        orderResponse.setCreationDate(source.getCreationDate());

        Set<OrderItemResponse> orderItemResponses = new HashSet<>();
        for (OrderItem orderItem : source.getOrderItems()) {
            orderItemResponses.add(orderItemResponseConverter.convert(orderItem));
        }
        orderResponse.setOrderItems(orderItemResponses);
        return orderResponse;
    }
}
