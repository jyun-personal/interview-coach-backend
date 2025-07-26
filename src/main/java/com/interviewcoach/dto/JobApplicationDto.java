package com.interviewcoach.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationDto {
    private UUID id;
    private Long userId; // For associating with a user
    @NotBlank(message = "Job title is required")
    private String title;
    @NotBlank(message = "Job description is required")
    private String description;
    private OffsetDateTime applicationDate;
    private LocalDate expectedInterviewDate;
    private String status; // e.g., PENDING, IN_PROGRESS, COMPLETED
    private List<String> tags; // Optional: for future tag integration
}