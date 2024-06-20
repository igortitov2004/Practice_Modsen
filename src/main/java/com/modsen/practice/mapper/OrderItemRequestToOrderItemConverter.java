package com.modsen.practice.mapper;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.Product;
import org.springframework.core.convert.converter.Converter;

public class OrderItemRequestToOrderItemConverter implements Converter<OrderItemRequest, OrderItem> {
    @Override
    public OrderItem convert(OrderItemRequest source) {
        Order order = new Order();
        Product product = new Product();
        order.setId(source.getOrderId());
        product.setId(source.getProductId());

        OrderItem orderItem = new OrderItem();
        orderItem.setCount(source.getCount());
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setId(source.getId());

        return orderItem;
    }
}
