package com.modsen.practice.service.impl;

import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.entity.Order;
import com.modsen.practice.exception.OrderNotExistException;
import com.modsen.practice.repository.OrderRepository;
import com.modsen.practice.service.OrderService;

import lombok.AllArgsConstructor;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("Order with id " + id + " not found"));
        return conversionService.convert(order, OrderResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return orderRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)))
                .getContent()
                .stream()
                .map(order -> conversionService.convert(order, OrderResponse.class))
                .toList();
    }

    @Override
    public List<OrderResponse> getAllByUserId(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return orderRepository.findByUser_id(userId, PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)))
                .stream()
                .map(order -> conversionService.convert(order, OrderResponse.class))
                .toList();
    }

    @Override
    public OrderResponse save(OrderRequest request) {
        var order = orderRepository.save(Objects.requireNonNull(
                conversionService.convert(request, Order.class)));
        return conversionService.convert(order, OrderResponse.class);
    }

    @Override
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotExistException("Order with id " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResponse update(OrderRequest request) {
        if (orderRepository.findById(request.getId()).isPresent()) {
            return save(request);
        } else {
            throw new OrderNotExistException("Order with id " + request.getId() + " not found");
        }
    }
}
