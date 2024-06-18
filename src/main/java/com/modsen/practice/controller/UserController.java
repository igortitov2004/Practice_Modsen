package com.modsen.practice.controller;


import com.modsen.practice.dto.UserRequestTo;
import com.modsen.practice.dto.UserResponseTo;
import com.modsen.practice.service.ICrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ICrudService<UserRequestTo, UserResponseTo> userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseTo> read(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseTo>> readAll(
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortOrder) {
        return new ResponseEntity<>(userService.getAll(pageNumber, pageSize, sortBy, sortOrder),
                HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<UserResponseTo> create(@RequestBody UserRequestTo user) {
        return new ResponseEntity<>(userService.save(user), HttpStatusCode.valueOf(201));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseTo> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatusCode.valueOf(204));
    }

    @PutMapping()
    public ResponseEntity<UserResponseTo> update(@RequestBody UserRequestTo user) {
        return new ResponseEntity<>(userService.update(user), HttpStatusCode.valueOf(200));
    }
}
