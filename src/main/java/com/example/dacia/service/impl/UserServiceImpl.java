package com.example.dacia.service.impl;

import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.UserRegistrationRequest;
import com.example.dacia.model.entities.User;
import com.example.dacia.service.UserService;
import com.example.dacia.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional
    public String registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPasswordHash(BCrypt.hashpw(request.password(), BCrypt.gensalt(12)));
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }
}
