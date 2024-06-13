package com.modsen.practice.controller;



import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserResponse userResponse = userService.getById(id);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll(
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortOrder) {
        List<UserResponse> users = userService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(users, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest user) {
        UserResponse userResponse = userService.save(user);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(201));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long id) {
        UserResponse userResponse = userService.delete(id);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(204));
    }

    @PutMapping()
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest user) {
        UserResponse userResponse = userService.update(user);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(200));
    }
}
