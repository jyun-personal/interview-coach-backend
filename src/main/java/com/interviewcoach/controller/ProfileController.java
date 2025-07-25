package com.interviewcoach.controller;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.dto.ProfileResponseDto;
import com.interviewcoach.service.IProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile")
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    @PostMapping
    public ResponseEntity<?> createProfile(@PathVariable Long userId, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = profileService.createProfile(userId, profileRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileResponseDto);
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
        ProfileResponseDto profileResponseDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok().body(profileResponseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable Long userId, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = profileService.updateProfile(userId, profileRequestDto);
        return ResponseEntity.accepted().body(profileResponseDto);
    }
}
