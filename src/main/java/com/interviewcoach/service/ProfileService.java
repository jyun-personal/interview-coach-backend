package com.interviewcoach.service;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.dto.ProfileResponseDto;
import com.interviewcoach.model.Profile;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.ProfileRepository;
import com.interviewcoach.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProfileResponseDto createProfile(Long userId, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfile() != null) {
            throw new RuntimeException("User already has a profile");
        }

        Profile profile = modelMapper.map(profileRequestDto, Profile.class);

        profile.setUser(user);

        Profile savedProfile = profileRepository.save(profile);

        return modelMapper.map(savedProfile, ProfileResponseDto.class);
    }

    @Override
    public ProfileResponseDto getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfile() == null) {
            throw new RuntimeException("User has no profile");
        }

        Profile userProfile = user.getProfile();

        return modelMapper.map(userProfile, ProfileResponseDto.class);
    }

    @Override
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfile() == null) {
            throw new RuntimeException("User has no profile. You have to create a new one.");
        }

        Profile oldProfile = user.getProfile();

        modelMapper.map(profileRequestDto, oldProfile);

        Profile updatedProfile = profileRepository.save(oldProfile);

        return modelMapper.map(updatedProfile, ProfileResponseDto.class);
    }
}
