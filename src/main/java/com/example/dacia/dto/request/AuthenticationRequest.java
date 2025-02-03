package com.example.dacia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email(message = "Correct Email Required")
    @NotNull(message = "Email cannot be Null")
    private String email;
    @Size(min = 8,message = "password length cannot be less than 8")
    @NotNull(message = "Password cannot be NUll")
    String password;
}