package com.estore.e_strore_backend.customer.controller;

import com.estore.e_strore_backend.customer.dto.ProfileRequest;
import com.estore.e_strore_backend.customer.dto.ProfileResponse;
import com.estore.e_strore_backend.customer.service.ProfileService;
import com.estore.e_strore_backend.shared.dto.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createOrUpdateProfile(
            @PathVariable Long userId,
            @RequestBody @Valid ProfileRequest request,
            BindingResult bindingResult) {

        // Check validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }

        ProfileResponse response = profileService.createOrUpdateProfile(userId, request);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getProfileByUserId(@PathVariable Long userId) {
        ProfileResponse response = profileService.getProfileByUserId(userId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}