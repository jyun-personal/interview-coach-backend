package com.interviewcoach.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.interviewcoach.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDto {

    private boolean success;
    private String message;
    private Long id; // User ID
    private String username; // Added username
    private String email;
    private UserRole role; // Added role
}
