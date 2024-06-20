package com.modsen.practice.service.impl;

import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductResponse getById(Long id) {
        return null;
    }

    @Override
    public List<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public ProductResponse save(ProductRequest request) {
        return null;
    }

    @Override
    public ProductResponse delete(Long id) {
        return null;
    }

    @Override
    public ProductResponse update(ProductRequest request) {
        return null;
    }
}
