package com.example.dacia.service;

import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.response.DemoResponse;
import com.example.dacia.model.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final UserRepository userRepository;

    public DemoResponse getDemo(Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        return user.map(value -> DemoResponse.builder()
                .id(value.getId())
                .name(value.getName())
                .role(value.getRole())
                .email(value.getEmail())
                .createdAt(value.getCreatedAt())
                .build()).orElse(null);
    }
}
