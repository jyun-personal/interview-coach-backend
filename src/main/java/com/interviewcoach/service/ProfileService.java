package com.interviewcoach.service;

import com.interviewcoach.dto.ProfileRequestDto;
import com.interviewcoach.dto.ProfileResponseDto;
import com.interviewcoach.exception.BadRequestException;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.Profile;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.ProfileRepository;
import com.interviewcoach.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    @Transactional // Ensure profile creation is part of a transaction
    public ProfileResponseDto createProfile(Long userId, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        if (user.getProfile() != null) {
            throw new BadRequestException("User already has a profile."); // Use BadRequestException
        }

        Profile profile = modelMapper.map(profileRequestDto, Profile.class);
        profile.setUser(user); // Link profile to user
//        profile.setId(user.getId()); // Set profile ID to match user ID due to @MapsId

        Profile savedProfile = profileRepository.save(profile);

        user.setProfile(savedProfile);

        // Link the newly created profile back to the user entity if not already cascaded
        // User entity's @OneToOne(mappedBy="user", cascade=CascadeType.ALL) should handle this automatically.
        // For explicitness: user.setProfile(savedProfile); userRepository.save(user); // If not cascading properly

        return mapProfileToResponseDto(savedProfile);
    }

    @Override
    public ProfileResponseDto getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        if (user.getProfile() == null) {
            throw new ResourceNotFoundException("Profile", "User ID", userId); // More specific error
        }

        Profile userProfile = user.getProfile();
        return mapProfileToResponseDto(userProfile);
    }

    @Override
    @Transactional // Ensure profile update is part of a transaction
    public ProfileResponseDto updateProfile(Long userId, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        if (user.getProfile() == null) {
            throw new ResourceNotFoundException("Profile", "User ID", userId); // More specific error
        }

        Profile existingProfile = user.getProfile();

        // Use modelMapper to update existing entity from DTO
        modelMapper.map(profileRequestDto, existingProfile);

        Profile updatedProfile = profileRepository.save(existingProfile);

        return mapProfileToResponseDto(updatedProfile);
    }

    // Helper method to map Profile entity to ProfileResponseDto
    private ProfileResponseDto mapProfileToResponseDto(Profile profile) {
        ProfileResponseDto dto = modelMapper.map(profile, ProfileResponseDto.class);
        
        // Manually set the User ID and other User fields
        if (profile.getUser() != null) {
            dto.setId(profile.getUser().getId()); // Set User's ID (Long), not Profile's UUID
            dto.setUsername(profile.getUser().getUsername());
            dto.setEmail(profile.getUser().getEmail());
        }
        return dto;
    }
}
