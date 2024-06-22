package com.modsen.practice.service.impl;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.entity.Category;
import com.modsen.practice.entity.Product;
import com.modsen.practice.exception.CategoryExistenceException;
import com.modsen.practice.exception.IncorrectDataException;
import com.modsen.practice.exception.UserExistenceException;
import com.modsen.practice.mapper.CategoryRequestToCategoryConverter;
import com.modsen.practice.mapper.CategoryToCategoryResponseConverter;
import com.modsen.practice.mapper.UserRequestToUserConverter;
import com.modsen.practice.mapper.UserToUserResponseConverter;
import com.modsen.practice.repository.CategoryRepository;
import com.modsen.practice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryResponse getById(Long id) {
        if (id != null && categoryRepository.findById(id).isPresent()) {
            var category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
            return new CategoryToCategoryResponseConverter().convert(category);
        }
        else throw new CategoryExistenceException();
    }

    @Override
    public Page<CategoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        var categories = categoryRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortOrder)));
        return categories.map(new CategoryToCategoryResponseConverter()::convert);
    }

    @Override
    public CategoryResponse save(CategoryRequest request) {
        if (request.getName() != null) {
            var category = categoryRepository.save(new CategoryRequestToCategoryConverter().convert(request));
            return new CategoryToCategoryResponseConverter().convert(category);
        } else throw new IncorrectDataException();
    }

    @Override
    public void delete(Long id) {
        if (id != null && categoryRepository.findById(id).isPresent()) categoryRepository.deleteById(id);
        else throw new CategoryExistenceException();
    }

    @Override
    public CategoryResponse update(CategoryRequest request) {
        if (categoryRepository.findById(request.getId()).isPresent())
            return save(request);
        else throw new CategoryExistenceException();
    }
}
