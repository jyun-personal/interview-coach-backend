package com.interviewcoach.service;

import com.interviewcoach.dto.AuthRequestDto;
import com.interviewcoach.dto.AuthResponseDto;
import com.interviewcoach.dto.LoginRequestDto;

public interface IAuthService {

    AuthResponseDto signup(AuthRequestDto authRequestDto);

    AuthResponseDto login(LoginRequestDto loginRequestDto);
}
