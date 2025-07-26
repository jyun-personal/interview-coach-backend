package com.interviewcoach.service;

import com.interviewcoach.dto.JobApplicationDto;

import java.util.List;
import java.util.UUID;

public interface IJobApplicationService {
    JobApplicationDto createJobApplication(Long userId, JobApplicationDto jobApplicationDto);

    List<JobApplicationDto> getAllJobApplicationsForUser(Long userId);

    JobApplicationDto getJobApplicationById(UUID jobApplicationId, Long userId);

    JobApplicationDto updateJobApplication(UUID jobApplicationId, Long userId, JobApplicationDto jobApplicationDto);

    void deleteJobApplication(UUID jobApplicationId, Long userId);
}