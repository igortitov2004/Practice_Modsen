package com.modsen.practice.service;



import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getById(Long id);

    List<CategoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryResponse save(CategoryRequest request);

    CategoryResponse delete(Long id);

    CategoryResponse update(CategoryRequest request);
}