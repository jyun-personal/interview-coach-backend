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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

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
        jobApplication.setApplicationDate(OffsetDateTime.now());
        jobApplication.setExpectedInterviewDate(jobApplicationDto.getExpectedInterviewDate());
        jobApplication.setTitle(jobApplicationDto.getTitle());
        jobApplication.setDescription(jobApplicationDto.getDescription());
        jobApplication.setStatus("PENDING");

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);
        return mapToDto(savedJobApplication);
    }

    @Override
    public Page<JobApplicationDto> getAllJobApplicationsForUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        Page<JobApplication> jobApplicationsPage = jobApplicationRepository.findAllByUser(user, pageable);
        return jobApplicationsPage.map(this::mapToDto);
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

    @Override
    @Transactional
    public Page<JobApplicationDto> searchJobApplications(Long userId, String searchTerm, Pageable pageable) {
        Page<JobApplication> applications = searchTerm == null || searchTerm.isEmpty()
                ? jobApplicationRepository.findByUserId(userId, pageable)
                : jobApplicationRepository.searchByUserIdAndTerm(userId, searchTerm, pageable);
        return applications.map(this::mapToDto);
    }

    private JobApplicationDto mapToDto(JobApplication jobApplication) {
        JobApplicationDto dto = modelMapper.map(jobApplication, JobApplicationDto.class);
        dto.setUserId(jobApplication.getUser().getId());
        return dto;
    }
}