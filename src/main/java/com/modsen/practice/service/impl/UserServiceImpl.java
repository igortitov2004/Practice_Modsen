package com.modsen.practice.service.impl;

import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse getById(Long id) {
        return null;
    }

    @Override
    public List<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortUser) {
        return null;
    }

    @Override
    public UserResponse save(UserRequest requestTo) {
        return null;
    }

    @Override
    public UserResponse delete(Long id) {
        return null;
    }

    @Override
    public UserResponse update(UserRequest requestTo) {
        return null;
    }
}
