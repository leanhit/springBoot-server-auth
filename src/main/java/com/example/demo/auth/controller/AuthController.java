package com.example.demo.auth.controller;

import com.example.demo.auth.dto.*;
import com.example.demo.auth.model.Auth;

import com.example.demo.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(authService.getUsers());
    }

    @PutMapping("/update-role")
    public ResponseEntity<Auth> updateUserRole(@RequestBody UpdateRoleRequest request) {
        Auth updatedUser = authService.updateUserRole(request);
        return ResponseEntity.ok(updatedUser);
    }
}
