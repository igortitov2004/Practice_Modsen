package com.modsen.practice.controller;

import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.service.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order_items")
public class OrderItemController {

    private final IOrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> read(@PathVariable Long id) {
        OrderItemResponse orderItemResponse = orderItemService.getById(id);
        return new ResponseEntity<>(orderItemResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<OrderItemResponse>> readAll(
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortOrder) {
        List<OrderItemResponse> orderItems = orderItemService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(orderItems, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> create(@RequestBody OrderItemRequest orderItem) {
        OrderItemResponse orderItemResponse = orderItemService.save(orderItem);
        return new ResponseEntity<>(orderItemResponse, HttpStatusCode.valueOf(201));

    }

    @PutMapping()
    public ResponseEntity<OrderItemResponse> update(@RequestBody OrderItemRequest orderItem) {
        OrderItemResponse orderItemResponse = orderItemService.update(orderItem);
        return new ResponseEntity<>(orderItemResponse, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemResponse> delete(@PathVariable Long id) {
        OrderItemResponse orderItemResponse = orderItemService.delete(id);
        return new ResponseEntity<>(orderItemResponse, HttpStatusCode.valueOf(204));
    }

}
