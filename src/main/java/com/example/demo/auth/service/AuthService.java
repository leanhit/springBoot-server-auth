package com.example.demo.auth.service;

import com.example.demo.auth.dto.*;
import com.example.demo.auth.model.*;
import com.example.demo.auth.repository.AuthRepository;
import com.example.demo.auth.security.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already taken");
        }

        Auth user = Auth.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        authRepository.save(user);
        String token = jwtUtils.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        Auth user = authRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
