package com.modsen.practice.service.impl;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Override
    public OrderItemResponse getById(Long id) {
        return null;
    }

    @Override
    public List<OrderItemResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrderItem) {
        return null;
    }

    @Override
    public OrderItemResponse save(OrderItemRequest requestTo) {
        return null;
    }

    @Override
    public OrderItemResponse delete(Long id) {
        return null;
    }

    @Override
    public OrderItemResponse update(OrderItemRequest requestTo) {
        return null;
    }
}
