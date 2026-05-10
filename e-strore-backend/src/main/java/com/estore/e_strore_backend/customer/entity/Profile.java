package com.estore.e_strore_backend.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String phone;

    private String address;

    private String city;

    private String country;

    // One-to-one relationship with User
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}