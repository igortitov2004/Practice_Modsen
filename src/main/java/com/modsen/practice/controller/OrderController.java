package com.modsen.practice.controller;


import com.modsen.practice.dto.Marker;
import com.modsen.practice.dto.OrderRequest;
import com.modsen.practice.dto.OrderResponse;
import com.modsen.practice.service.OrderService;
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
@RequestMapping("/api/orders")
@Validated
public class OrderController {


    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById( @PathVariable @Min(1) Long id) {
        OrderResponse orderResponse = orderService.getById(id);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc")String sortOrder,
            @RequestParam(required = false, name = "user") @Min(1) Long userId){
        List<OrderResponse> orders;
        if(userId != null)
        {
            orders = orderService.getAllByUserId(userId,pageNumber, pageSize, sortBy, sortOrder);
        }else{
            orders = orderService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        }
        return new ResponseEntity<>(orders, HttpStatusCode.valueOf(200));
    }

    @PostMapping()
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<OrderResponse> save(@RequestBody @Valid OrderRequest order) {
        OrderResponse orderResponse = orderService.save(order);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(201));
    }

    @PutMapping()
    @Validated(Marker.OnUpdate.class)
    public ResponseEntity<OrderResponse> update(@RequestBody @Valid OrderRequest order) {
        OrderResponse orderResponse = orderService.update(order);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> delete(@PathVariable @Min(1) Long id) {
        OrderResponse orderResponse = orderService.delete(id);
        return new ResponseEntity<>(orderResponse, HttpStatusCode.valueOf(204));
    }


}
