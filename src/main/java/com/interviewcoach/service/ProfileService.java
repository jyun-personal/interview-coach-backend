package com.interviewcoach.service;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.dto.ProfileResponseDto;
import com.interviewcoach.model.Profile;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.ProfileRepository;
import com.interviewcoach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ProfileResponseDto createProfile(Long userId, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfile() != null) {
            throw new RuntimeException("User already has a profile");
        }

        Profile profile = Profile.builder()
                .firstName(profileRequestDto.getFirstName())
                .lastName(profileRequestDto.getLastName())
                .bio(profileRequestDto.getBio())
                .street(profileRequestDto.getStreet())
                .city(profileRequestDto.getCity())
                .state(profileRequestDto.getState())
                .country(profileRequestDto.getCountry())
                .build();

        profile.setUser(user);

        Profile savedProfile = profileRepository.save(profile);

        return null;
    }

    @Override
    public ProfileResponseDto getProfileByUserId(Long userId) {
        return null;
    }

    @Override
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto profileRequestDto) {
        return null;
    }
}
