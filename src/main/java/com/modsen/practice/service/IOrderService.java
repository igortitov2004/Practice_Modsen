package com.modsen.practice.service;

import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse getById(Long id);

    List<OrderResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderResponse save(OrderRequest requestTo);

    OrderResponse delete(Long id);

    OrderResponse update(OrderRequest requestTo);
}
