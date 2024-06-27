package com.modsen.practice.service;



import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse getById(Long id);

    List<OrderResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<OrderResponse> getAllByUserId(Long userId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderResponse save(OrderRequest requestTo);

    void delete(Long id);
}
