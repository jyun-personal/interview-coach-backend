package com.interviewcoach.service;

import com.interviewcoach.dto.AuthRequestDto;
import com.interviewcoach.dto.AuthResponseDto;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto signup(AuthRequestDto authRequestDto) {

        if (userRepository.findByEmail(authRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        String hashedPassword = passwordEncoder.encode(authRequestDto.getPassword());

        User user = User.builder()
                .email(authRequestDto.getEmail())
                .passwordHash(hashedPassword)
                .build();

        User savedUser = userRepository.save(user);

        return AuthResponseDto.builder()
                .success(true)
                .message("User registered successfully. Please login.")
                .build();
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        User user = userRepository.findByEmail(authRequestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User", "email", authRequestDto.getEmail()));

        if (!passwordEncoder.matches(authRequestDto.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return AuthResponseDto.builder()
                .success(true)
                .message("Login Successful")
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
