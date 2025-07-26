package com.interviewcoach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponseDto {
    private Long id; // Matches User ID
    private String firstName;
    private String lastName;
    private String phone;
    private String bio;
    private String resumeText; // Added resumeText
    // Removed: street, city, state, country
    private String username; // For display, fetched from User
    private String email; // For display, fetched from User
}
