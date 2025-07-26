package com.interviewcoach.service;

import com.interviewcoach.dto.JobApplicationDto;
import com.interviewcoach.exception.BadRequestException;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.JobApplication;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.JobApplicationRepository;
import com.interviewcoach.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobApplicationService implements IJobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public JobApplicationDto createJobApplication(Long userId, JobApplicationDto jobApplicationDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        JobApplication jobApplication = modelMapper.map(jobApplicationDto, JobApplication.class);
        jobApplication.setUser(user);
        jobApplication.setApplicationDate(OffsetDateTime.now()); // Set application date automatically
        jobApplication.setExpectedInterviewDate(jobApplicationDto.getExpectedInterviewDate()); // From DTO, can be null
        jobApplication.setTitle(jobApplicationDto.getTitle());
        jobApplication.setDescription(jobApplicationDto.getDescription());
        jobApplication.setStatus("PENDING"); // Initial status

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);
        return mapToDto(savedJobApplication);
    }

    @Override
    public List<JobApplicationDto> getAllJobApplicationsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId)); // Ensure user exists
        List<JobApplication> jobApplications = jobApplicationRepository.findByUserId(userId);
        return jobApplications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationDto getJobApplicationById(UUID jobApplicationId, Long userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));

        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }
        return mapToDto(jobApplication);
    }

    @Override
    @Transactional
    public JobApplicationDto updateJobApplication(UUID jobApplicationId, Long userId, JobApplicationDto jobApplicationDto) {
        JobApplication existingJobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));

        if (!existingJobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }

        // Update fields from DTO
        existingJobApplication.setTitle(jobApplicationDto.getTitle());
        existingJobApplication.setDescription(jobApplicationDto.getDescription());
        existingJobApplication.setExpectedInterviewDate(jobApplicationDto.getExpectedInterviewDate());
        existingJobApplication.setStatus(jobApplicationDto.getStatus()); // Allow status update

        JobApplication updatedJobApplication = jobApplicationRepository.save(existingJobApplication);
        return mapToDto(updatedJobApplication);
    }

    @Override
    @Transactional
    public void deleteJobApplication(UUID jobApplicationId, Long userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));

        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }
        jobApplicationRepository.delete(jobApplication);
    }

    private JobApplicationDto mapToDto(JobApplication jobApplication) {
        JobApplicationDto dto = modelMapper.map(jobApplication, JobApplicationDto.class);
        dto.setUserId(jobApplication.getUser().getId()); // Ensure userId is set in DTO
        // Tags are not in MVP, but can be added here if needed in DTO
        return dto;
    }
}