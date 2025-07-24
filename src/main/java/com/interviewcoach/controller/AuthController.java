package com.interviewcoach.controller;

import com.interviewcoach.dto.AuthRequestDto;
import com.interviewcoach.dto.AuthResponseDto;
import com.interviewcoach.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.signup(authRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authService.login(authRequestDto);

        return ResponseEntity.ok().body(authResponseDto);
    }
}
