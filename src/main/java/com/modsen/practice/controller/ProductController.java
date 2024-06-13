package com.modsen.practice.controller;

import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        List<ProductResponse> response = productService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse response = productService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
        ProductResponse response = productService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest product) {
        ProductResponse response = productService.save(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductResponse> update(@RequestBody ProductRequest product) {
        ProductResponse response = productService.update(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
