package com.example.dacia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = "Username cannot be Null")
    private String username;
    @Size(min = 8,message = "password length cannot be less than 8")
    @NotNull(message = "Password cannot be NUll")
    private String password;
    @Email(message = "Correct Email Required")
    @NotNull(message = "Email cannot be Null")
    private String email;
}
