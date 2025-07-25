package com.interviewcoach.service;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.dto.ProfileResponseDto;

public interface IProfileService {
    ProfileResponseDto createProfile(Long userId, ProfileRequestDto profileRequestDto);

    ProfileResponseDto getProfileByUserId(Long userId);

    ProfileResponseDto updateProfile(Long userId, ProfileRequestDto profileRequestDto);

    // void deleteProfile(Long userId); // Not sure if we need this? Under what circumstances would a user wants to delete their profile?
}
