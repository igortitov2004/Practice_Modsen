package com.modsen.practice.service.impl;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.mapper.OrderItemRequestToOrderItemConverter;
import com.modsen.practice.mapper.OrderItemToOrderItemResponseConverter;
import com.modsen.practice.repository.CategoryRepository;
import com.modsen.practice.repository.OrderItemRepository;
import com.modsen.practice.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItemResponse getById(Long id) {
        var orderItem = orderItemRepository.findById(id).orElseThrow(RuntimeException::new);
        return new OrderItemToOrderItemResponseConverter().convert(orderItem);
    }

    @Override
    public Page<OrderItemResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrderItem) {
        orderItemRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
        return null;
    }

    @Override
    public OrderItemResponse save(OrderItemRequest request) {
        orderItemRepository.save(Objects.requireNonNull(new OrderItemRequestToOrderItemConverter().convert(request)));
        return null;
    }

    @Override
    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItemResponse update(OrderItemRequest requestTo) {
        return null;
    }
}
