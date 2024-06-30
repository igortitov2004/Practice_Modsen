package com.modsen.practice.service.impl;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.entity.Category;
import com.modsen.practice.exception.CategoryIsNotExistsException;
import com.modsen.practice.exception.IncorrectDataException;
import com.modsen.practice.mapper.CategoryRequestToCategoryConverter;
import com.modsen.practice.mapper.CategoryToCategoryResponseConverter;
import com.modsen.practice.repository.CategoryRepository;
import com.modsen.practice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ConversionService conversionService;

    @Transactional(readOnly = true)
    @Override
    public CategoryResponse getById(Long id) {
            var category = categoryRepository.findById(id)
                    .orElseThrow(()-> new CategoryIsNotExistsException("Category with this id is not exists"));
            return conversionService.convert(category, CategoryResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return categoryRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)))
                .getContent()
                .stream()
                .map((o)-> conversionService.convert(o,CategoryResponse.class))
                .toList();
    }


    @Transactional
    @Override
    public CategoryResponse save(CategoryRequest request) {
        if (request.getName() != null) {
            var category = categoryRepository.save(Objects.requireNonNull(conversionService.convert(request, Category.class)));
            return conversionService.convert(category, CategoryResponse.class);
        } else throw new IncorrectDataException("Invalid data received");
    }


    @Transactional
    @Override
    public void delete(Long id) {
        if (id != null && categoryRepository.findById(id).isPresent()) categoryRepository.deleteById(id);
        else throw new CategoryIsNotExistsException("Category with this id is not exists");
    }


    @Transactional
    @Override
    public CategoryResponse update(CategoryRequest request) {
        if (categoryRepository.findById(request.getId()).isPresent())
            return save(request);
        else throw new CategoryIsNotExistsException("Category with this id is not exists");
    }
}
