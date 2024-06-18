package com.example.practice_modsen_shop.services;

import com.example.practice_modsen_shop.dto.UserRequestTo;
import com.example.practice_modsen_shop.dto.UserResponseTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements ICrudService<UserRequestTo, UserResponseTo> {

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
