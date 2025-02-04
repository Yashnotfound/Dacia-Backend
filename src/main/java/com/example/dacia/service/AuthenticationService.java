package com.example.dacia.service;

import com.example.dacia.config.JwtService;
import com.example.dacia.dao.PasswordResetTokenRepository;
import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.AuthenticationRequest;
import com.example.dacia.dto.request.RegisterRequest;
import com.example.dacia.dto.response.AuthenticationResponse;
import com.example.dacia.exceptionHandler.EmailAlreadyExistsException;
import com.example.dacia.exceptionHandler.ExpiredTokenException;
import com.example.dacia.exceptionHandler.InvalidTokenException;
import com.example.dacia.exceptionHandler.UserNotFoundException;
import com.example.dacia.model.entities.PasswordResetToken;
import com.example.dacia.model.entities.User;
import com.example.dacia.model.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        var user = User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.END_USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(60))
                .build();

        passwordResetTokenRepository.save(resetToken);
        sendResetEmail(user.getEmail(), token);
    }

    private void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Reset link: http://localhost:8080/reset-password?token=" + token);
        mailSender.send(message);
    }

    @Transactional
    public void completePasswordReset(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
//        user.setPasswordResetToken(null);
        userRepository.saveAndFlush(user);
        passwordResetTokenRepository.delete(resetToken);
    }

}
