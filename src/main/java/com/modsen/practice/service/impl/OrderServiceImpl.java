package com.modsen.practice.service.impl;

import com.modsen.practice.auth.UserVODetails;
import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.User;
import com.modsen.practice.exception.OrderNotExistException;
import com.modsen.practice.repository.OrderRepository;
import com.modsen.practice.repository.UserRepository;
import com.modsen.practice.service.OrderItemService;
import com.modsen.practice.service.OrderService;

import com.modsen.practice.service.UserService;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;

    private final ModelMapper modelMapper;

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

    @Transactional
    @Override
    public OrderResponse save(OrderRequest request) {
        var order = modelMapper.map(request, Order.class);
        order.setUser(getCurrentUser());
        var savedOrder = orderRepository.save(order);
        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItemRequest orderItemRequest : request.getOrderItems()) {
            OrderItemResponse orderItemResponse = orderItemService.save(orderItemRequest, savedOrder);
            orderItems.add(modelMapper.map(orderItemResponse, OrderItem.class));
        }
        savedOrder.setOrderItems(orderItems);
        return mapToResponse(savedOrder);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotExistException("Order with id " + id + " not found");
        }
        orderRepository.deleteById(id);
    }

    private User getCurrentUser() {
        UserVODetails userVODetails = (UserVODetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userVODetails.getUser();
    }

    private OrderResponse mapToResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
}
