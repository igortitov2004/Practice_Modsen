package com.modsen.practice.controller;

import com.modsen.practice.dto.Marker;
import com.modsen.practice.dto.OrderItemRequest;
import com.modsen.practice.dto.OrderItemResponse;
import com.modsen.practice.service.OrderItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order_items")
@Validated
public class OrderItemController {
    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable @Min(1) Long id) {
        OrderItemResponse orderItemResponse = orderItemService.getById(id);
        return new ResponseEntity<>(orderItemResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc")String sortOrder) {
        List<OrderItemResponse> orderItems = orderItemService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(orderItems, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<OrderItemResponse> save(@RequestBody @Valid OrderItemRequest orderItem) {
        OrderItemResponse orderItemResponse = orderItemService.save(orderItem);
        return new ResponseEntity<>(orderItemResponse, HttpStatusCode.valueOf(201));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Min(1) Long id) {
        orderItemService.delete(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatusCode.valueOf(204));
    }

}
