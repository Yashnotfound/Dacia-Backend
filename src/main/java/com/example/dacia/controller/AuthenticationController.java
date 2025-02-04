package com.example.dacia.controller;

import com.example.dacia.dto.request.AuthenticationRequest;
import com.example.dacia.dto.request.RegisterRequest;
import com.example.dacia.dto.request.ForgotPasswordRequest;
import com.example.dacia.dto.response.AuthenticationResponse;
import com.example.dacia.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authenticationService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok("Reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestBody String newPassword
    ) {
        authenticationService.completePasswordReset(token, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }



}