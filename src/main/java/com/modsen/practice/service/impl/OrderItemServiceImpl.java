package com.modsen.practice.service.impl;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.entity.Order;
import com.modsen.practice.entity.OrderItem;
import com.modsen.practice.entity.Product;
import com.modsen.practice.exception.orderItem.OrderItemIsNotExistsException;
import com.modsen.practice.repository.OrderItemRepository;
import com.modsen.practice.service.OrderItemService;
import com.modsen.practice.service.ProductService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ConversionService conversionService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public OrderItemResponse getById(Long id) {
        var orderItem = orderItemRepository.findById(id)
                .orElseThrow(()-> new OrderItemIsNotExistsException("Order item with this id is not exists"));
        return conversionService.convert(orderItem,OrderItemResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderItemResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrderItem) {
        return orderItemRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)))
                .getContent()
                .stream()
                .map((o)-> conversionService.convert(o,OrderItemResponse.class))
                .toList();
    }

    @Transactional
    @Override
    public OrderItemResponse save(OrderItemRequest request, Order order) {
        var orderItem = OrderItem.builder()
                .order(order)
                .count(request.getCount())
                .product(modelMapper.map(productService.getById(request.getProductId()), Product.class))
                .build();
        var savedOrderItem = orderItemRepository.save(orderItem);
        return mapToResponse(savedOrderItem);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!orderItemRepository.existsOrderItemById(id)) {
            throw new OrderItemIsNotExistsException("Order item with this id is not exists");
        }
        orderItemRepository.deleteById(id);
    }

    private OrderItemResponse mapToResponse(OrderItem orderItem){
        return modelMapper.map(orderItem,OrderItemResponse.class);
    }



}
