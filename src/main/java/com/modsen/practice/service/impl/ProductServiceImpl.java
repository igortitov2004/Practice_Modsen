package com.modsen.practice.service.impl;

import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.entity.Category;
import com.modsen.practice.entity.Product;
import com.modsen.practice.exception.product.ProductIsNotExistsException;
import com.modsen.practice.repository.ProductRepository;
import com.modsen.practice.service.CategoryService;
import com.modsen.practice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ConversionService conversionService;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductIsNotExistsException("Product with this id is not exists"));
        return conversionService.convert(product, ProductResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy))).getContent()
                .stream()
                .map(product -> conversionService.convert(product, ProductResponse.class))
                .toList();
    }

    @Transactional
    @Override
    public List<ProductResponse> getAllByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return productRepository.findByCategory_id(categoryId, PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)))
                .stream()
                .map(product -> conversionService.convert(product, ProductResponse.class))
                .toList();
    }

    @Transactional
    @Override
    public ProductResponse save(ProductRequest request) {
        var product = conversionService.convert(request, Product.class);
        product.setCategory(modelMapper.map(categoryService.getById(request.getCategoryId()), Category.class));
        var savedProduct = productRepository.save(product);
        return conversionService.convert(savedProduct, ProductResponse.class);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!productRepository.existsOrderItemById(id)) {
            throw new ProductIsNotExistsException("Product with this id is not exists");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ProductResponse update(ProductRequest request) {
        if (productRepository.findById(request.getId()).isPresent()) {
            return save(request);
        } else {
            throw new ProductIsNotExistsException("Product with this id is not exists");
        }
    }
}
