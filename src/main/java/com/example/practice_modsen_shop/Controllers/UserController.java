package com.example.practice_modsen_shop.Controllers;


import com.example.practice_modsen_shop.DTO.OrderItemResponseTo;
import com.example.practice_modsen_shop.DTO.UserRequestTo;
import com.example.practice_modsen_shop.DTO.UserResponseTo;
import com.example.practice_modsen_shop.Service.IService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IService<UserRequestTo, UserResponseTo> userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseTo> read(@PathVariable Long id) {
        try {
            UserResponseTo userTo = userService.getById(id);

            return new ResponseEntity<>(userTo, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseTo>> readAll(@RequestParam int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam String sortBy, @RequestParam String sortOrder) {
        try {
            List<UserResponseTo> userTos = userService.getAll(pageNumber, pageSize, sortBy, sortOrder);
            return new ResponseEntity<>(userTos, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseTo> create(@RequestBody UserRequestTo user) {
        try {
            UserResponseTo userTo = userService.save(user);
            return new ResponseEntity<>(userTo, HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseTo> delete(@PathVariable Long id) {
        try {
            UserResponseTo userTo = userService.delete(id);
            return new ResponseEntity<>(userTo, HttpStatusCode.valueOf(204));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<UserResponseTo> update(@RequestBody UserRequestTo user) {
        try {
            UserResponseTo userTo = userService.update(user);
            return new ResponseEntity<>(userTo, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
