package com.modsen.practice.service;



import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserResponse getById(Long id);

    Page<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortUser);

    UserResponse save(UserRequest request);

    void delete(Long id);

    UserResponse update(UserRequest request);
}
