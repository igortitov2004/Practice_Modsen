package com.modsen.practice.controller;


import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.entity.Order;
import com.modsen.practice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {


    private final IOrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> read(@PathVariable Long id) {
        OrderResponse orderResponse = orderService.getById(id);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> readAll(
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortOrder) {
        List<OrderResponse> orders = orderService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(orders, HttpStatusCode.valueOf(200));
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest order) {
        OrderResponse orderResponse = orderService.save(order);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(201));
    }

    @PutMapping()
    public ResponseEntity<OrderResponse> update(@RequestBody OrderRequest order) {
        OrderResponse orderResponse = orderService.update(order);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> delete(@PathVariable Long id) {
        OrderResponse orderResponse = orderService.delete(id);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(204));
    }


}
