package com.example.dacia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email Format is Invalid")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Minimum length 8")
        String password
){

}
