package com.interviewcoach.service;

import com.interviewcoach.dto.AuthRequestDto;
import com.interviewcoach.dto.AuthResponseDto;

public interface IAuthService {

    AuthResponseDto signup(AuthRequestDto authRequestDto);
    AuthResponseDto login(AuthRequestDto authRequestDto);
}
