package com.interviewcoach.service;

import com.interviewcoach.dto.JobApplicationDto;
import com.interviewcoach.exception.BadRequestException;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.JobApplication;
import com.interviewcoach.model.User;
import com.interviewcoach.repository.JobApplicationRepository;
import com.interviewcoach.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper; // Mock ModelMapper as well

    @InjectMocks
    private JobApplicationService jobApplicationService;

    private User testUser;
    private JobApplication testJobApp;
    private JobApplicationDto testJobAppDto;
    private Long userId;
    private UUID jobAppId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        jobAppId = UUID.randomUUID();

        testUser = new User();
        testUser.setId(userId);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");

        testJobApp = new JobApplication();
        testJobApp.setId(jobAppId);
        testJobApp.setTitle("Software Engineer");
        testJobApp.setDescription("Job Description...");
        testJobApp.setUser(testUser);
        testJobApp.setApplicationDate(OffsetDateTime.now());
        testJobApp.setStatus("PENDING");

        testJobAppDto = new JobApplicationDto();
        testJobAppDto.setId(jobAppId);
        testJobAppDto.setTitle("Software Engineer");
        testJobAppDto.setDescription("Job Description...");
        testJobAppDto.setUserId(userId);
        testJobAppDto.setStatus("PENDING");
    }

    @Test
    void createJobApplication_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(modelMapper.map(any(JobApplicationDto.class), eq(JobApplication.class))).thenReturn(testJobApp);
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(testJobApp);
        when(modelMapper.map(any(JobApplication.class), eq(JobApplicationDto.class))).thenReturn(testJobAppDto);

        JobApplicationDto result = jobApplicationService.createJobApplication(userId, testJobAppDto);

        assertNotNull(result);
        assertEquals(jobAppId, result.getId());
        assertEquals("Software Engineer", result.getTitle());
        verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
    }

    @Test
    void createJobApplication_UserNotFound_ThrowsResourceNotFoundException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                jobApplicationService.createJobApplication(userId, testJobAppDto));
        verify(jobApplicationRepository, never()).save(any(JobApplication.class));
    }

    @Test
    void getAllJobApplicationsForUser_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<JobApplication> jobAppsList = Arrays.asList(testJobApp);
        Page<JobApplication> jobAppsPage = new PageImpl<>(jobAppsList, pageable, jobAppsList.size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(jobApplicationRepository.findAllByUser(testUser, pageable)).thenReturn(jobAppsPage);
        when(modelMapper.map(any(JobApplication.class), eq(JobApplicationDto.class))).thenReturn(testJobAppDto);

        Page<JobApplicationDto> result = jobApplicationService.getAllJobApplicationsForUser(userId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testJobAppDto.getTitle(), result.getContent().get(0).getTitle());
        verify(jobApplicationRepository, times(1)).findAllByUser(testUser, pageable);
    }

    @Test
    void getAllJobApplicationsForUser_UserNotFound_ThrowsResourceNotFoundException() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                jobApplicationService.getAllJobApplicationsForUser(userId, pageable));
        verify(jobApplicationRepository, never()).findAllByUser(any(User.class), any(Pageable.class));
    }

    @Test
    void getJobApplicationById_Success() {
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.of(testJobApp));
        when(modelMapper.map(any(JobApplication.class), eq(JobApplicationDto.class))).thenReturn(testJobAppDto);

        JobApplicationDto result = jobApplicationService.getJobApplicationById(jobAppId, userId);

        assertNotNull(result);
        assertEquals(jobAppId, result.getId());
        verify(jobApplicationRepository, times(1)).findById(jobAppId);
    }

    @Test
    void getJobApplicationById_NotFound_ThrowsResourceNotFoundException() {
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                jobApplicationService.getJobApplicationById(jobAppId, userId));
    }

    @Test
    void getJobApplicationById_AccessDenied_ThrowsBadRequestException() {
        User otherUser = new User();
        otherUser.setId(2L);
        testJobApp.setUser(otherUser); // Job app belongs to another user
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.of(testJobApp));

        assertThrows(BadRequestException.class, () ->
                jobApplicationService.getJobApplicationById(jobAppId, userId));
    }

    @Test
    void updateJobApplication_Success() {
        JobApplicationDto updatedDto = new JobApplicationDto();
        updatedDto.setTitle("Updated Title");
        updatedDto.setDescription("Updated Description");
        updatedDto.setStatus("COMPLETED");
        updatedDto.setExpectedInterviewDate(LocalDate.now().plusMonths(1));

        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.of(testJobApp));
        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(testJobApp);
        when(modelMapper.map(any(JobApplication.class), eq(JobApplicationDto.class))).thenReturn(updatedDto);

        JobApplicationDto result = jobApplicationService.updateJobApplication(jobAppId, userId, updatedDto);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("COMPLETED", result.getStatus());
        verify(jobApplicationRepository, times(1)).save(testJobApp);
        verify(modelMapper, times(1)).map(updatedDto, testJobApp); // Verify mapping for update
    }

    @Test
    void updateJobApplication_NotFound_ThrowsResourceNotFoundException() {
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                jobApplicationService.updateJobApplication(jobAppId, userId, testJobAppDto));
        verify(jobApplicationRepository, never()).save(any(JobApplication.class));
    }

    @Test
    void updateJobApplication_AccessDenied_ThrowsBadRequestException() {
        User otherUser = new User();
        otherUser.setId(2L);
        testJobApp.setUser(otherUser);
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.of(testJobApp));

        assertThrows(BadRequestException.class, () ->
                jobApplicationService.updateJobApplication(jobAppId, userId, testJobAppDto));
        verify(jobApplicationRepository, never()).save(any(JobApplication.class));
    }

    @Test
    void deleteJobApplication_Success() {
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.of(testJobApp));

        jobApplicationService.deleteJobApplication(jobAppId, userId);

        verify(jobApplicationRepository, times(1)).delete(testJobApp);
    }

    @Test
    void deleteJobApplication_NotFound_ThrowsResourceNotFoundException() {
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                jobApplicationService.deleteJobApplication(jobAppId, userId));
        verify(jobApplicationRepository, never()).delete(any(JobApplication.class));
    }

    @Test
    void deleteJobApplication_AccessDenied_ThrowsBadRequestException() {
        User otherUser = new User();
        otherUser.setId(2L);
        testJobApp.setUser(otherUser);
        when(jobApplicationRepository.findById(jobAppId)).thenReturn(Optional.of(testJobApp));

        assertThrows(BadRequestException.class, () ->
                jobApplicationService.deleteJobApplication(jobAppId, userId));
        verify(jobApplicationRepository, never()).delete(any(JobApplication.class));
    }
}