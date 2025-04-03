package com.example.demo.auth.service;

import com.example.demo.auth.dto.*;
import com.example.demo.auth.model.*;
import com.example.demo.auth.repository.AuthRepository;
import com.example.demo.auth.security.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors; // <--- IMPORT NÀY
import com.example.demo.auth.model.Auth; // <--- Đảm bảo import Model User nếu chưa có
import org.springframework.security.access.AccessDeniedException;


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
        System.out.println("Login attempt: " + request.getUsername());

        Auth user = authRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    System.err.println("User not found: " + request.getUsername());
                    return new RuntimeException("User not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.err.println("Invalid password for user: " + request.getUsername());
        throw new RuntimeException("Invalid password");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        System.out.println("Generated token for user: " + request.getUsername());
        
        return new AuthResponse(token);
    }

    public List<UserResponse> getUsers() {
        return authRepository.findAll().stream()
            .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name()))
            .collect(Collectors.toList());
    }
    
    public Auth updateUserRole(UpdateRoleRequest request) {
        Auth user = authRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.valueOf(request.getRole()));
        return authRepository.save(user);
    }

}
