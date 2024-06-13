package com.example.practice_modsen_shop.Service;

import com.example.practice_modsen_shop.DTO.UserRequestTo;
import com.example.practice_modsen_shop.DTO.UserResponseTo;

import java.util.List;

public class UserService implements IService<UserRequestTo, UserResponseTo> {

    @Override
    public UserResponseTo save(UserRequestTo item) {
        return null;
    }

    @Override
    public List<UserResponseTo> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public UserResponseTo update(UserRequestTo item) {
        return null;
    }

    @Override
    public UserResponseTo delete(Long id) {
        return null;
    }

    @Override
    public UserResponseTo getById(Long id) {
        return null;
    }
}
