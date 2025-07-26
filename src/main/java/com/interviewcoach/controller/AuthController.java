package com.interviewcoach.controller;

import com.interviewcoach.dto.AuthRequestDto;
import com.interviewcoach.dto.AuthResponseDto;
import com.interviewcoach.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Base path
// @CrossOrigin("*") // Removed, now handled by WebConfig.java
public class AuthController {

    @Autowired
    private IAuthService authService; // Injected the interface

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@Valid @RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.signup(authRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.login(authRequestDto);
        return ResponseEntity.ok().body(authResponseDto);
    }
}
