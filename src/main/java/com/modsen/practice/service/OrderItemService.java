package com.modsen.practice.service;

import com.modsen.practice.dto.OrderItemRequestTo;
import com.modsen.practice.dto.OrderItemResponseTo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OrderItemService implements ICrudService<OrderItemRequestTo, OrderItemResponseTo> {

    @Override
    public OrderItemResponseTo save(OrderItemRequestTo item) {
        return null;
    }

    @Override
    public List<OrderItemResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public OrderItemResponseTo update(OrderItemRequestTo item) {
        return null;
    }

    @Override
    public OrderItemResponseTo delete(Long id) {
        return null;
    }

    @Override
    public OrderItemResponseTo getById(Long id) {
        return null;
    }
}
