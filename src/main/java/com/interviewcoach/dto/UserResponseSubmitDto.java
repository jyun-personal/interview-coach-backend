package com.interviewcoach.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseSubmitDto {
    @NotBlank(message = "Response text cannot be empty")
    private String responseText;
    // We don't need questionId/jobApplicationId here as they come from URL path variables
}