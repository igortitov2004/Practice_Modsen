package com.modsen.practice.controller;


import com.modsen.practice.dto.OrderRequestTo;
import com.modsen.practice.dto.OrderResponseTo;
import com.modsen.practice.service.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private ICrudService<OrderRequestTo, OrderResponseTo> orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseTo> read(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getById(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponseTo>> readAll(
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortOrder) {
        return new ResponseEntity<>(orderService.getAll(pageNumber, pageSize, sortBy, sortOrder),
                HttpStatusCode.valueOf(200));
    }

    @PostMapping()
    public ResponseEntity<OrderResponseTo> create(@RequestBody OrderRequestTo order) {
        return new ResponseEntity<>(orderService.save(order), HttpStatusCode.valueOf(201));
    }

    @PutMapping()
    public ResponseEntity<OrderResponseTo> update(@RequestBody OrderRequestTo order) {
        return new ResponseEntity<>(orderService.update(order), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseTo> delete(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.delete(id), HttpStatusCode.valueOf(204));
    }


}
