package com.estore.e_strore_backend.customer.repository;

import com.estore.e_strore_backend.customer.entity.Profile;
import com.estore.e_strore_backend.customer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    // Find profile by user
    Optional<Profile> findByUser(User user);

    // Find profile by user id
    Optional<Profile> findByUserId(Long userId);

    // Check if profile exists for a user
    boolean existsByUserId(Long userId);
}