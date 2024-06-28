package com.modsen.practice.service;



import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse getById(Long id);

    List<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    List<ProductResponse> getAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse save(ProductRequest request);

    void delete(Long id);

    ProductResponse update(ProductRequest request);
}
