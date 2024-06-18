package com.modsen.practice.controller;

import com.modsen.practice.dto.ProductRequestTo;
import com.modsen.practice.dto.ProductResponseTo;
import com.modsen.practice.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ICrudService<ProductRequestTo, ProductResponseTo> productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseTo>> getAll(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                           @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                           @RequestParam(required = false, defaultValue = "desc") String sortOrder) {
        return ResponseEntity.status(200).body(productService.getAll(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseTo> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productService.delete(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseTo> save(@RequestBody ProductRequestTo product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping
    public ResponseEntity<ProductResponseTo> update(@RequestBody ProductRequestTo product) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(product));
    }
}
