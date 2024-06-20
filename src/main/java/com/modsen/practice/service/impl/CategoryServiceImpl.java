package com.modsen.practice.service.impl;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryResponse getById(Long id) {
        return null;
    }

    @Override
    public List<CategoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public CategoryResponse save(CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse delete(Long id) {
        return null;
    }

    @Override
    public CategoryResponse update(CategoryRequest request) {
        return null;
    }
}
