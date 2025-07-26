package com.interviewcoach.service;

import com.interviewcoach.dto.JobApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IJobApplicationService {
    JobApplicationDto createJobApplication(Long userId, JobApplicationDto jobApplicationDto);

    Page<JobApplicationDto> getAllJobApplicationsForUser(Long userId, Pageable pageable);

    JobApplicationDto getJobApplicationById(UUID jobApplicationId, Long userId);

    JobApplicationDto updateJobApplication(UUID jobApplicationId, Long userId, JobApplicationDto jobApplicationDto);

    void deleteJobApplication(UUID jobApplicationId, Long userId);
}