package com.example.dacia.service;

import com.example.dacia.dto.request.UserRegistrationRequest;
import com.example.dacia.dto.response.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest userRegistrationRequest);
}
