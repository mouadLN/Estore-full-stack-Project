package com.estore.e_strore_backend.customer.controller;

import com.estore.e_strore_backend.customer.dto.AuthResponse;
import com.estore.e_strore_backend.customer.dto.LoginRequest;
import com.estore.e_strore_backend.customer.dto.RegisterRequest;
import com.estore.e_strore_backend.customer.service.UserService;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request,
                                      BindingResult bindingResult) {

        // Check validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        AuthResponse response = userService.register(request);

        // Check if registration failed (email exists)
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(response.getMessage()));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request,
                                   BindingResult bindingResult) {

        // Check validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        AuthResponse response = userService.login(request);

        // Check if login failed
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(response.getMessage()));
        }

        return ResponseEntity.ok(response);
    }
}