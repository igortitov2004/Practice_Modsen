package com.modsen.practice.auth;

import com.modsen.practice.auth.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.practice.entity.User;
import com.modsen.practice.enumeration.Gender;
import com.modsen.practice.enumeration.UserRole;
import com.modsen.practice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserVODetailsService userVODetailsService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .middleName(request.getMiddleName())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .email(request.getEmail())
                .login(request.getLogin())
                .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                .role(UserRole.CUSTOMER)
                .build();
        repository.save(user);
        var accessToken = jwtService.generateToken(new UserVODetails(user));
        var refreshToken = jwtService.generateRefreshToken(new UserVODetails(user));
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(UserRole.CUSTOMER.toString())
                .userData(user.getEmail()).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserData(),
                        request.getPassword()
                )
        );
        var user = userVODetailsService.loadUserByUsername(request.getUserData());
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getAuthorities().toString())
                .userData(user.getUsername()).build();
    }
    @SneakyThrows
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user =  userVODetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
