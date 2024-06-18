package com.modsen.practice.controller;

import com.modsen.practice.dto.CategoryRequestTo;
import com.modsen.practice.dto.CategoryResponseTo;
import com.modsen.practice.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    ICrudService<CategoryRequestTo, CategoryResponseTo> categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseTo>> getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                           @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                           @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseTo> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoryService.delete(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseTo> save(@RequestBody CategoryRequestTo category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(category));
    }

    @PutMapping
    public ResponseEntity<CategoryResponseTo> update(@RequestBody CategoryRequestTo category) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(category));
    }
}
