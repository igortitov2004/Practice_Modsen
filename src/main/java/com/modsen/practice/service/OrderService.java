package com.modsen.practice.service;

import com.modsen.practice.dto.OrderRequestTo;
import com.modsen.practice.dto.OrderResponseTo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OrderService implements ICrudService<OrderRequestTo, OrderResponseTo> {

    @Override
    public OrderResponseTo save(OrderRequestTo item) {
        return null;
    }

    @Override
    public List<OrderResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public OrderResponseTo update(OrderRequestTo item) {
        return null;
    }

    @Override
    public OrderResponseTo delete(Long id) {
        return null;
    }

    @Override
    public OrderResponseTo getById(Long id) {
        return null;
    }
}
