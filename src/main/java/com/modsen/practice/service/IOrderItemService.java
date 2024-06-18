package com.modsen.practice.service;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;

import java.util.List;

public interface IOrderItemService {
    OrderItemResponse getById(Long id);

    List<OrderItemResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrderItem);

    OrderItemResponse save(OrderItemRequest requestTo);

    OrderItemResponse delete(Long id);

    OrderItemResponse update(OrderItemRequest requestTo);
}
