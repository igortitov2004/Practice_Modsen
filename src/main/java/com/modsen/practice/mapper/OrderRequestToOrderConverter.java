package com.modsen.practice.mapper;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.User;
import com.modsen.practice.enumeration.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class OrderRequestToOrderConverter implements Converter<OrderRequest, Order> {

    @Autowired
    private ConversionService conversionService;

    @Override
    public Order convert(OrderRequest source) {
        Order order = new Order();
        User user = new User();
        user.setId(source.getId());
        order.setUser(user);
        order.setStreet(source.getStreet());
        order.setCity(source.getCity());
        order.setStatus(OrderStatus.valueOf(source.getStatus()));
        order.setCreationDate(source.getCreationDate());
        order.setHouseNumber(source.getHouseNumber());
        order.setApartmentNumber(source.getApartmentNumber());
        order.setPrice(source.getPrice());

        Set<OrderItem> orderItemSet = new HashSet<>();
        for (OrderItemRequest orderItemRequest : source.getOrderItems()) {
            OrderItem orderItem = conversionService.convert(orderItemRequest, OrderItem.class);
            orderItem.setOrder(order);
            orderItemSet.add(orderItem);
        }
        order.setOrderItems(orderItemSet);
        return order;
    }
}
