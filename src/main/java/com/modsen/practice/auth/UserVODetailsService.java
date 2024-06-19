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
        Optional<User> user;
        if(username.contains("@")){
            user = userRepository.findUserByEmail(username);
        }else{
            user = userRepository.findUserByLogin(username);
        }
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserVODetails(user.get());
    }
}
