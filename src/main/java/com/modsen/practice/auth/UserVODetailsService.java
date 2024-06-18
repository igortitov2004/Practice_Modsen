package com.modsen.practice.auth;

import com.modsen.practice.entity.User;
import com.modsen.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserVODetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("The user was not found"));
            return new UserVODetails(user);
        }
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("The user was not found"));
        return new UserVODetails(user);
    }
}
