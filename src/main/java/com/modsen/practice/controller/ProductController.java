package com.modsen.practice.controller;

import com.modsen.practice.dto.Marker;
import com.modsen.practice.dto.ProductRequest;
import com.modsen.practice.dto.ProductResponse;
import com.modsen.practice.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
@PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
                                                        @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
                                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false, defaultValue = "desc") String sortOrder,
                                                        @RequestParam(required = false, name = "category") @Min(1) Long categoryId) {
        List<ProductResponse> response;
        if (categoryId != null) {
            response = productService.getAllByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response = productService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable @Min(1) Long id) {
        ProductResponse response = productService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Min(1) Long id) {
        productService.delete(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest product) {
        ProductResponse response = productService.save(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    @Validated(Marker.OnUpdate.class)
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductRequest product) {
        ProductResponse response = productService.update(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
