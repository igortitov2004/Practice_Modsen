package com.modsen.practice.mapper;

import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserResponseConverter implements Converter<User, UserResponse> {
    @Override
    public UserResponse convert(User source) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(source.getId());
        userResponse.setFirstname(source.getFirstname());
        userResponse.setLastname(source.getLastname());
        userResponse.setLogin(source.getLogin());
        userResponse.setGender(source.getGender().toString());
        userResponse.setMiddleName(source.getMiddleName());
        userResponse.setEmail(source.getEmail());
        userResponse.setRole(source.getRole().toString());
        userResponse.setBirthDate(source.getBirthDate());
        userResponse.setPhoneNumber(source.getPhoneNumber());
        return userResponse;
    }
}
