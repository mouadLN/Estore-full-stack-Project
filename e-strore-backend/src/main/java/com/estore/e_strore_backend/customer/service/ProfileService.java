package com.estore.e_strore_backend.customer.service;

import com.estore.e_strore_backend.customer.dto.ProfileRequest;
import com.estore.e_strore_backend.customer.dto.ProfileResponse;
import com.estore.e_strore_backend.customer.entity.Profile;
import com.estore.e_strore_backend.customer.entity.User;
import com.estore.e_strore_backend.customer.repository.ProfileRepository;
import com.estore.e_strore_backend.customer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    // Create or update profile for a user
    public ProfileResponse createOrUpdateProfile(Long userId, ProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return null;
        }

        Profile profile = profileRepository.findByUserId(userId).orElse(null);

        if (profile == null) {
            // Create new profile
            profile = new Profile();
            profile.setUser(user);
        }

        // Update fields
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setCountry(request.getCountry());

        Profile savedProfile = profileRepository.save(profile);

        // Convert to response DTO
        ProfileResponse response = new ProfileResponse();
        response.setId(savedProfile.getId());
        response.setPhone(savedProfile.getPhone());
        response.setAddress(savedProfile.getAddress());
        response.setCity(savedProfile.getCity());
        response.setCountry(savedProfile.getCountry());
        response.setUserId(user.getId());
        response.setUserEmail(user.getEmail());
        response.setUserFirstName(user.getFirstName());
        response.setUserLastName(user.getLastName());

        return response;
    }

    // Get profile by user id
    public ProfileResponse getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId).orElse(null);

        if (profile == null) {
            return null;
        }

        User user = profile.getUser();

        ProfileResponse response = new ProfileResponse();
        response.setId(profile.getId());
        response.setPhone(profile.getPhone());
        response.setAddress(profile.getAddress());
        response.setCity(profile.getCity());
        response.setCountry(profile.getCountry());
        response.setUserId(user.getId());
        response.setUserEmail(user.getEmail());
        response.setUserFirstName(user.getFirstName());
        response.setUserLastName(user.getLastName());

        return response;
    }
}