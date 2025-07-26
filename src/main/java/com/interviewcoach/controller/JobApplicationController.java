package com.interviewcoach.controller;

import com.interviewcoach.dto.JobApplicationDto;
import com.interviewcoach.service.IJobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/job-applications") // base path
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
    public ResponseEntity<Page<JobApplicationDto>> getAllJobApplicationsForUser(
            @RequestHeader("X-User-ID") Long userId,
            @PageableDefault(size = 10, sort = "applicationDate,desc") Pageable pageable) { // <--- Use @PageableDefault for default page/size/sort
        Page<JobApplicationDto> jobApplicationsPage = jobApplicationService.getAllJobApplicationsForUser(userId, pageable);
        return ResponseEntity.ok(jobApplicationsPage);
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