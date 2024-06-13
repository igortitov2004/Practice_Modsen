package com.modsen.practice.service;



import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getById(Long id);

    List<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortUser);

    UserResponse save(UserRequest requestTo);

    UserResponse delete(Long id);

    UserResponse update(UserRequest requestTo);
}
