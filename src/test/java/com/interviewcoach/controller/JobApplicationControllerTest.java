package com.interviewcoach.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewcoach.dto.JobApplicationDto;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.service.IJobApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class) // Focuses on testing only the JobApplicationController
class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to simulate HTTP requests

    @MockBean // Spring will inject a Mockito mock of IJobApplicationService into the controller
    private IJobApplicationService jobApplicationService;

    @Autowired
    private ObjectMapper objectMapper; // Used to convert objects to JSON and vice versa

    private Long userId;
    private UUID jobAppId;
    private JobApplicationDto jobApplicationDto;

    @BeforeEach
    void setUp() {
        userId = 1L;
        jobAppId = UUID.randomUUID();
        jobApplicationDto = new JobApplicationDto();
        jobApplicationDto.setId(jobAppId);
        jobApplicationDto.setUserId(userId);
        jobApplicationDto.setTitle("Test Job App");
        jobApplicationDto.setDescription("This is a test job description.");
        jobApplicationDto.setApplicationDate(OffsetDateTime.now());
        jobApplicationDto.setStatus("PENDING");
    }

    @Test
    void createJobApplication_Success() throws Exception {
        when(jobApplicationService.createJobApplication(eq(userId), any(JobApplicationDto.class)))
                .thenReturn(jobApplicationDto);

        mockMvc.perform(post("/api/job-applications")
                        .header("X-User-ID", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobApplicationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(jobAppId.toString()))
                .andExpect(jsonPath("$.title").value("Test Job App"));

        verify(jobApplicationService, times(1)).createJobApplication(eq(userId), any(JobApplicationDto.class));
    }

    @Test
    void createJobApplication_InvalidInput_ReturnsBadRequest() throws Exception {
        JobApplicationDto invalidDto = new JobApplicationDto(); // Missing title and description
        invalidDto.setUserId(userId);

        mockMvc.perform(post("/api/job-applications")
                        .header("X-User-ID", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.title").exists())
                .andExpect(jsonPath("$.validationErrors.description").exists());

        verify(jobApplicationService, never()).createJobApplication(anyLong(), any(JobApplicationDto.class));
    }

    @Test
    void getAllJobApplicationsForUser_Success() throws Exception {
        List<JobApplicationDto> jobAppsList = Arrays.asList(jobApplicationDto);
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobApplicationDto> jobAppsPage = new PageImpl<>(jobAppsList, pageable, jobAppsList.size());

        when(jobApplicationService.getAllJobApplicationsForUser(eq(userId), any(Pageable.class)))
                .thenReturn(jobAppsPage);

        mockMvc.perform(get("/api/job-applications")
                        .header("X-User-ID", userId)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "applicationDate,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(jobAppId.toString()))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(jobApplicationService, times(1)).getAllJobApplicationsForUser(eq(userId), any(Pageable.class));
    }

    @Test
    void getJobApplicationById_Success() throws Exception {
        when(jobApplicationService.getJobApplicationById(eq(jobAppId), eq(userId)))
                .thenReturn(jobApplicationDto);

        mockMvc.perform(get("/api/job-applications/{id}", jobAppId)
                        .header("X-User-ID", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(jobAppId.toString()))
                .andExpect(jsonPath("$.title").value("Test Job App"));

        verify(jobApplicationService, times(1)).getJobApplicationById(eq(jobAppId), eq(userId));
    }

    @Test
    void getJobApplicationById_NotFound_ReturnsNotFound() throws Exception {
        when(jobApplicationService.getJobApplicationById(eq(jobAppId), eq(userId)))
                .thenThrow(new ResourceNotFoundException("JobApplication", "ID", jobAppId));

        mockMvc.perform(get("/api/job-applications/{id}", jobAppId)
                        .header("X-User-ID", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());

        verify(jobApplicationService, times(1)).getJobApplicationById(eq(jobAppId), eq(userId));
    }

    @Test
    void updateJobApplication_Success() throws Exception {
        JobApplicationDto updatedDto = new JobApplicationDto();
        updatedDto.setId(jobAppId);
        updatedDto.setTitle("Updated Title");
        updatedDto.setDescription("Updated Description");
        updatedDto.setStatus("COMPLETED");
        updatedDto.setExpectedInterviewDate(LocalDate.now().plusMonths(1));

        when(jobApplicationService.updateJobApplication(eq(jobAppId), eq(userId), any(JobApplicationDto.class)))
                .thenReturn(updatedDto);

        mockMvc.perform(put("/api/job-applications/{id}", jobAppId)
                        .header("X-User-ID", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(jobApplicationService, times(1)).updateJobApplication(eq(jobAppId), eq(userId), any(JobApplicationDto.class));
    }

    @Test
    void deleteJobApplication_Success() throws Exception {
        doNothing().when(jobApplicationService).deleteJobApplication(eq(jobAppId), eq(userId));

        mockMvc.perform(delete("/api/job-applications/{id}", jobAppId)
                        .header("X-User-ID", userId))
                .andExpect(status().isNoContent());

        verify(jobApplicationService, times(1)).deleteJobApplication(eq(jobAppId), eq(userId));
    }

    @Test
    void deleteJobApplication_NotFound_ReturnsNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("JobApplication", "ID", jobAppId))
                .when(jobApplicationService).deleteJobApplication(eq(jobAppId), eq(userId));

        mockMvc.perform(delete("/api/job-applications/{id}", jobAppId)
                        .header("X-User-ID", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());

        verify(jobApplicationService, times(1)).deleteJobApplication(eq(jobAppId), eq(userId));
    }
}