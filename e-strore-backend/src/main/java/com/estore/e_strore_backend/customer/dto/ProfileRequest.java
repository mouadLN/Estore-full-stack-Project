package com.estore.e_strore_backend.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileRequest {

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String address;
    private String city;
    private String country;
}