package com.example.practice_modsen_shop.Service;

import com.example.practice_modsen_shop.DTO.OrderRequestTo;
import com.example.practice_modsen_shop.DTO.OrderResponseTo;

import java.util.List;

public class OrderService implements IService<OrderRequestTo, OrderResponseTo> {

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
