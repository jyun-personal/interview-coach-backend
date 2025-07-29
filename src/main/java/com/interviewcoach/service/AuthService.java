package com.interviewcoach.service;

import com.interviewcoach.dto.AuthRequestDto;
import com.interviewcoach.dto.AuthResponseDto;
import com.interviewcoach.dto.LoginRequestDto;
import com.interviewcoach.enums.UserRole;
import com.interviewcoach.exception.BadRequestException;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.Profile;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.ProfileRepository;
import com.interviewcoach.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository; // For creating initial profile

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional // Ensures both user and profile are saved or none
    public AuthResponseDto signup(AuthRequestDto authRequestDto) {
        if (userRepository.findByEmail(authRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException("User with this email already exists.");
        }
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username is already taken.");
        }

        String hashedPassword = passwordEncoder.encode(authRequestDto.getPassword());

        User newUser = User.builder()
                .username(authRequestDto.getUsername())
                .email(authRequestDto.getEmail())
                .passwordHash(hashedPassword)
                .role(UserRole.JOB_SEEKER) // Use Enum here
                .build();

        User savedUser = userRepository.save(newUser);

        // Create an initial profile for the new user
        Profile initialProfile = new Profile(savedUser, authRequestDto.getFirstName(), authRequestDto.getLastName());
        profileRepository.save(initialProfile);
        savedUser.setProfile(initialProfile);

        return AuthResponseDto.builder()
                .success(true)
                .message("User registered successfully. Please login.")
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        // Login using email only (since LoginRequestDto only has email and password)
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "credentials", "provided")); // Use generic message for security

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Invalid email or password.");
        }

        return AuthResponseDto.builder()
                .success(true)
                .message("Login Successful")
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
