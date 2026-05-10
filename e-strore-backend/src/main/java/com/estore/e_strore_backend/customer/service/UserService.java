package com.estore.e_strore_backend.customer.service;

import com.estore.e_strore_backend.customer.dto.AuthResponse;
import com.estore.e_strore_backend.customer.dto.LoginRequest;
import com.estore.e_strore_backend.customer.dto.RegisterRequest;
import com.estore.e_strore_backend.customer.entity.User;
import com.estore.e_strore_backend.customer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Register new user
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(false, "Email already exists!");
        }

        // Create new user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword()) // Plain text for now
                .build();

        User savedUser = userRepository.save(user);

        return new AuthResponse(true, "Registration successful!",
                savedUser.getId(), savedUser.getEmail(),
                savedUser.getFirstName(), savedUser.getLastName());
    }

    // Login user
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        // Check if user exists and password matches
        if (user == null) {
            return new AuthResponse(false, "Email not found!");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Incorrect password!");
        }

        return new AuthResponse(true, "Login successful!",
                user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName());
    }
}