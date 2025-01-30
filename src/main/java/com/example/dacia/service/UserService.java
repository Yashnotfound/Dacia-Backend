package com.example.dacia.service;

import com.example.dacia.dto.request.UserLoginRequest;
import com.example.dacia.dto.request.UserRegistrationRequest;

public interface UserService {
    String registerUser(UserRegistrationRequest userRegistrationRequest);
    String loginUser(UserLoginRequest userLoginRequest);
}
