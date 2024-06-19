package com.modsen.practice.service.impl;

import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.service.OrderService;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse getById(Long id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public OrderResponse save(OrderRequest requestTo) {
        return null;
    }

    @Override
    public OrderResponse delete(Long id) {
        return null;
    }

    @Override
    public OrderResponse update(OrderRequest requestTo) {
        return null;
    }
}
