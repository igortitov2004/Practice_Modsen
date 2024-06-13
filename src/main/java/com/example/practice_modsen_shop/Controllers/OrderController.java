package com.example.practice_modsen_shop.Controllers;

import com.example.practice_modsen_shop.DTO.OrderItemResponseTo;
import com.example.practice_modsen_shop.DTO.OrderRequestTo;
import com.example.practice_modsen_shop.DTO.OrderResponseTo;
import com.example.practice_modsen_shop.Service.IService;
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
    private IService<OrderRequestTo, OrderResponseTo> orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseTo> read(@PathVariable Long id) {
        try {
            OrderResponseTo order = orderService.getById(id);
            return new ResponseEntity<OrderResponseTo>(order, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponseTo>> readAll(@RequestParam int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam String sortBy, @RequestParam String sortOrder) {
        try {
            List<OrderResponseTo> orderTos = orderService.getAll(pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(orderTos, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<OrderResponseTo> create(@RequestBody OrderRequestTo order) {
        try {
            OrderResponseTo orderResponseTo = orderService.save(order);
            return new ResponseEntity<OrderResponseTo>(orderResponseTo, HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<OrderResponseTo> update(@RequestBody OrderRequestTo order) {
        try {
            OrderResponseTo orderResponseTo = orderService.update(order);
            return new ResponseEntity<OrderResponseTo>(orderResponseTo, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseTo> delete(@PathVariable Long id) {
        try {
            OrderResponseTo orderResponseTo = orderService.delete(id);
            return new ResponseEntity<OrderResponseTo>(orderResponseTo, HttpStatusCode.valueOf(204));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
