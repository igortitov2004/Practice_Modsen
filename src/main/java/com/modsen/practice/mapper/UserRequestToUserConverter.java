package com.modsen.practice.mapper;

import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.entity.User;
import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.UserRole;
import org.springframework.core.convert.converter.Converter;

public class UserRequestToUserConverter implements Converter<UserRequest, User> {
    @Override
    public User convert(UserRequest source) {
        User user = new User();
        user.setId(source.getId());
        user.setFirstname(source.getFirstname());
        user.setLastname(source.getLastname());
        user.setLogin(source.getLogin());
        user.setGender(Gender.valueOf(source.getGender()));
        user.setMiddleName(source.getMiddleName());
        user.setEmail(source.getEmail());
        user.setRole(UserRole.valueOf(source.getRole()));
        user.setBirthDate(source.getBirthDate());
        user.setPhoneNumber(source.getPhoneNumber());
        user.setPasswordHash(source.getPasswordHash());
        return user;
    }
}
