package com.modsen.practice.controller;

import com.modsen.practice.dto.CategoryRequest;
import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                           @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                           @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        List<CategoryResponse> response = categoryService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable Long id) {
        CategoryResponse response = categoryService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest category) {
        CategoryResponse response = categoryService.save(category);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryResponse> update(@RequestBody CategoryRequest category) {
        CategoryResponse response = categoryService.update(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
