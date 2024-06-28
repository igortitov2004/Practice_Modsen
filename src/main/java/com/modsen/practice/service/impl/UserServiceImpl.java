package com.modsen.practice.service.impl;

import com.modsen.practice.dto.CategoryResponse;
import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.entity.User;
import com.modsen.practice.exception.CategoryIsNotExistsException;
import com.modsen.practice.exception.IncorrectDataException;
import com.modsen.practice.exception.UserIsNotExistsException;
import com.modsen.practice.mapper.UserRequestToUserConverter;
import com.modsen.practice.mapper.UserToUserResponseConverter;
import com.modsen.practice.repository.UserRepository;
import com.modsen.practice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConversionService conversionService;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(()-> new UserIsNotExistsException("User with this id is not exists"));
        return conversionService.convert(user, UserResponse.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortUser) {
        return userRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)))
                .getContent()
                .stream()
                .map((o)-> conversionService.convert(o,UserResponse.class))
                .toList();
    }


    @Transactional
    @Override
    public UserResponse save(UserRequest request) {
        if (request.getLogin() != null && request.getPasswordHash() != null) {
            var user = userRepository.save(Objects.requireNonNull(conversionService.convert(request, User.class)));
            return conversionService.convert(user, UserResponse.class);
        } else throw new IncorrectDataException("Invalid data received");
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (id != null && userRepository.findById(id).isPresent()) userRepository.deleteById(id);
        else throw new UserIsNotExistsException("User with this id is not exists");
    }


    @Transactional
    @Override
    public UserResponse update(UserRequest request) {
        if (userRepository.findById(request.getId()).isPresent())
            return save(request);
        else throw new UserIsNotExistsException("User with this id is not exists");
    }
}
