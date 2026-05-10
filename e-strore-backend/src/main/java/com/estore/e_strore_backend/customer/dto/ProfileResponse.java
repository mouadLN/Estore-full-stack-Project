package com.estore.e_strore_backend.customer.dto;

import lombok.Data;

@Data
public class ProfileResponse {
    private Long id;
    private String phone;
    private String address;
    private String city;
    private String country;
    private Long userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
}