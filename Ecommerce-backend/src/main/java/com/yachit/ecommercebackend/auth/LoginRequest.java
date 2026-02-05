package com.yachit.ecommercebackend.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;
}
