package com.interviewcoach.controller;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile")
public class ProfileController {

    @Autowired
    private IProfileService profileService;

    @PostMapping
    public ResponseEntity<?> createProfile(@PathVariable Long userId, @RequestBody ProfileRequestDto profileRequestDto) {
        profileService.createProfile(userId, profileRequestDto);
        return ResponseEntity.ok().build();
    }
}
