package com.modsen.practice.service.impl;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.mapper.OrderRequestToOrderConverter;
import com.modsen.practice.mapper.OrderToOrderResponseConverter;
import com.modsen.practice.repository.OrderItemRepository;
import com.modsen.practice.repository.OrderRepository;
import com.modsen.practice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse getById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        return new OrderToOrderResponseConverter().convert(order);
    }

    @Override
    public Page<OrderResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        orderRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
        return null;
    }

    @Override
    public Page<OrderResponse> getAllByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public OrderResponse save(OrderRequest request) {
        orderRepository.save(Objects.requireNonNull(new OrderRequestToOrderConverter().convert(request)));
        return null;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResponse update(OrderRequest requestTo) {
        return null;
    }
}
