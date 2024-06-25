package com.modsen.practice.service;



import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderResponse getById(Long id);

    Page<OrderResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    Page<OrderResponse> getAllByUserId(Long userId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    OrderResponse save(OrderRequest requestTo);

    void delete(Long id);

    OrderResponse update(OrderRequest requestTo);
}
