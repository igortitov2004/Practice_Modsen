package com.modsen.practice.controller;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.Marker;
import com.modsen.practice.service.CategoryService;
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
@RequestMapping("/api/categories")
@PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
                                                         @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
                                                         @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                         @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        List<CategoryResponse> response = categoryService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable @Min(1) Long id) {
        CategoryResponse response = categoryService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable @Min(1) Long id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryRequest category) {
        CategoryResponse response = categoryService.save(category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    @Validated(Marker.OnUpdate.class)
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest category) {
        CategoryResponse response = categoryService.update(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
