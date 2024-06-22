package com.modsen.practice.service;



import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderItemService {
    OrderItemResponse getById(Long id);

    List<OrderItemResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrderItem);

    OrderItemResponse save(OrderItemRequest requestTo);

    void delete(Long id);
}
