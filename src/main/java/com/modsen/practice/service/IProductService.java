package com.modsen.practice.service;

import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;

import java.util.List;

public interface IProductService {
    ProductResponse getById(Long id);

    List<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse save(ProductRequest request);

    ProductResponse delete(Long id);

    ProductResponse update(ProductRequest request);
}
