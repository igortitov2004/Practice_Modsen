package com.modsen.practice.service.impl;

import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.mapper.ProductRequestToProductConverter;
import com.modsen.practice.mapper.ProductToProductResponseConverter;
import com.modsen.practice.repository.ProductRepository;
import com.modsen.practice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse getById(Long id) {
        var product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        return new ProductToProductResponseConverter().convert(product);
    }

    @Override
    public Page<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public Page<ProductResponse> getAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
        return null;
    }

    @Override
    public ProductResponse save(ProductRequest request) {
        productRepository.save(Objects.requireNonNull(new ProductRequestToProductConverter().convert(request)));
        return null;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse update(ProductRequest request) {
        return null;
    }
}
