package com.example.practice_modsen_shop.Controllers;

import com.example.practice_modsen_shop.DTO.OrderItemRequestTo;
import com.example.practice_modsen_shop.DTO.OrderItemResponseTo;
import com.example.practice_modsen_shop.Service.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order_items")
public class OrderItemController {

    @Autowired
    private IService<OrderItemRequestTo, OrderItemResponseTo> orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseTo> read(@PathVariable Long id) {
        try {
            OrderItemResponseTo orderItemTo = orderItemService.getById(id);
            return new ResponseEntity<>(orderItemTo, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<OrderItemResponseTo>> readAll(@RequestParam int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam String sortBy, @RequestParam String sortOrder) {
        try {
            List<OrderItemResponseTo> orderItemTos = orderItemService.getAll(pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(orderItemTos, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseTo> create(@RequestBody OrderItemRequestTo orderItem) {
        try {
            OrderItemResponseTo orderItemTo = orderItemService.save(orderItem);
            return new ResponseEntity<>(orderItemTo, HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<OrderItemResponseTo> update(@RequestBody OrderItemRequestTo orderItem) {
        try {
            OrderItemResponseTo orderItemTo = orderItemService.update(orderItem);
            return new ResponseEntity<>(orderItemTo, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemResponseTo> delete(@PathVariable Long id) {
        try {
            OrderItemResponseTo orderItemTo = orderItemService.delete(id);
            return new ResponseEntity<>(orderItemTo, HttpStatusCode.valueOf(204));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
