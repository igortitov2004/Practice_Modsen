package com.modsen.practice.controller;



import com.modsen.practice.dto.Marker;
import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.service.UserService;
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
@RequestMapping("/api/users")
@Validated
public class UserController {


    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable @Min(1) Long id) {
        UserResponse userResponse = userService.getById(id);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc")String sortOrder) {
        List<UserResponse> users = userService.getAll(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(users, HttpStatusCode.valueOf(200));
    }

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<UserResponse> save(@RequestBody @Valid UserRequest user) {
        UserResponse userResponse = userService.save(user);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(201));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable @Min(1) Long id) {
        UserResponse userResponse = userService.delete(id);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(204));
    }

    @PutMapping()
    @Validated(Marker.OnUpdate.class)
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest user) {
        UserResponse userResponse = userService.update(user);
        return new ResponseEntity<>(userResponse, HttpStatusCode.valueOf(200));
    }
}
