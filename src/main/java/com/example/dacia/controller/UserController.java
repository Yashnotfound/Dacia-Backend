package com.example.dacia.controller;

import com.example.dacia.dto.request.UserRegistrationRequest;
import com.example.dacia.dto.response.UserResponse;
import com.example.dacia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
    UserResponse response = userService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}