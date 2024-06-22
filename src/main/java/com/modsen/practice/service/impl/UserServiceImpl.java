package com.modsen.practice.service.impl;

import com.modsen.practice.dto.UserRequest;
import com.modsen.practice.dto.UserResponse;
import com.modsen.practice.exception.IncorrectDataException;
import com.modsen.practice.exception.UserExistenceException;
import com.modsen.practice.mapper.UserRequestToUserConverter;
import com.modsen.practice.mapper.UserToUserResponseConverter;
import com.modsen.practice.repository.UserRepository;
import com.modsen.practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponse getById(Long id) {
        if (id != null && userRepository.findById(id).isPresent()) {
            var user = userRepository.findById(id).orElseThrow(RuntimeException::new);
            return new UserToUserResponseConverter().convert(user);
        }
        else throw new UserExistenceException();
    }

    @Override
    public Page<UserResponse> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortUser) {
        var users = userRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortUser)));
        return users.map(new UserToUserResponseConverter()::convert);
    }

    @Override
    public UserResponse save(UserRequest request) {
        if (request.getLogin() != null && request.getPasswordHash() != null) {
            var user = userRepository.save(new UserRequestToUserConverter().convert(request));
            return new UserToUserResponseConverter().convert(user);
        } else throw new IncorrectDataException();
    }

    @Override
    public void delete(Long id) {
        if (id != null && userRepository.findById(id).isPresent()) userRepository.deleteById(id);
        else throw new UserExistenceException();
    }

    @Override
    public UserResponse update(UserRequest request) {
        if (userRepository.findById(request.getId()).isPresent())
            return save(request);
        else throw new UserExistenceException();
    }
}
