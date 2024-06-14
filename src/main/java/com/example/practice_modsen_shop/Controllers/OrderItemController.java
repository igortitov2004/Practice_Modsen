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
        return new ResponseEntity<>(orderItemService.getById(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<OrderItemResponseTo>> readAll(
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortOrder) {
        return new ResponseEntity<>(orderItemService.getAll(pageNumber, pageSize, sortBy, sortOrder),
                HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseTo> create(@RequestBody OrderItemRequestTo orderItem) {
        return new ResponseEntity<>(orderItemService.save(orderItem), HttpStatusCode.valueOf(201));

    }

    @PutMapping()
    public ResponseEntity<OrderItemResponseTo> update(@RequestBody OrderItemRequestTo orderItem) {
        return new ResponseEntity<>(orderItemService.update(orderItem), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemResponseTo> delete(@PathVariable Long id) {
        return new ResponseEntity<>(orderItemService.delete(id), HttpStatusCode.valueOf(204));
    }

}
