package com.example.practice_modsen_shop.services;

import com.example.practice_modsen_shop.dto.OrderItemRequestTo;
import com.example.practice_modsen_shop.dto.OrderItemResponseTo;
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
