package com.modsen.practice.mapper;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.User;
import com.modsen.practice.enumeration.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderRequestToOrderConverter implements Converter<OrderRequest, Order> {

    private OrderItemRequestToOrderItemConverter orderItemRequestConverter;

    @Override
    public Order convert(OrderRequest source) {
        Order order = new Order();
        order.setStreet(source.getStreet());
        order.setCity(source.getCity());
        order.setStatus(OrderStatus.valueOf(source.getStatus()));
        order.setCreationDate(source.getCreationDate());
        order.setHouseNumber(source.getHouseNumber());
        order.setApartmentNumber(source.getApartmentNumber());
        order.setPrice(source.getPrice());
        return order;
    }
}
