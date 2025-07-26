package com.interviewcoach.controller;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.dto.ProfileResponseDto;
import com.interviewcoach.service.IProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/profile")
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    // The createProfile endpoint was removed
    // because the Profile is now created automatically when a User signs up via AuthService.

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
        ProfileResponseDto profileResponseDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok().body(profileResponseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable Long userId, @Valid @RequestBody ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = profileService.updateProfile(userId, profileRequestDto);
        return ResponseEntity.accepted().body(profileResponseDto); // Use HTTP 202 Accepted for update confirmation
    }
}
