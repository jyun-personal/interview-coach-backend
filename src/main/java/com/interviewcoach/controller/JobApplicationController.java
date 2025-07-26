package com.interviewcoach.controller;

import com.interviewcoach.dto.JobApplicationDto;
import com.interviewcoach.service.IJobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    @Autowired
    private IJobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationDto> createJobApplication(@RequestHeader("X-User-ID") Long userId,
                                                                  @Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplicationDto createdJobApplication = jobApplicationService.createJobApplication(userId, jobApplicationDto);
        return new ResponseEntity<>(createdJobApplication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationDto>> getAllJobApplicationsForUser(@RequestHeader("X-User-ID") Long userId) {
        List<JobApplicationDto> jobApplications = jobApplicationService.getAllJobApplicationsForUser(userId);
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationDto> getJobApplicationById(@PathVariable UUID id,
                                                                   @RequestHeader("X-User-ID") Long userId) {
        JobApplicationDto jobApplication = jobApplicationService.getJobApplicationById(id, userId);
        return ResponseEntity.ok(jobApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationDto> updateJobApplication(@PathVariable UUID id,
                                                                  @RequestHeader("X-User-ID") Long userId,
                                                                  @Valid @RequestBody JobApplicationDto jobApplicationDto) {
        JobApplicationDto updatedJobApplication = jobApplicationService.updateJobApplication(id, userId, jobApplicationDto);
        return ResponseEntity.ok(updatedJobApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable UUID id,
                                                     @RequestHeader("X-User-ID") Long userId) {
        jobApplicationService.deleteJobApplication(id, userId);
        return ResponseEntity.noContent().build();
    }
}