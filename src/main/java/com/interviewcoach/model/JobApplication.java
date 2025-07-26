package com.interviewcoach.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "application_date", nullable = false)
    @CreationTimestamp
    private OffsetDateTime applicationDate;

    @Column(name = "expected_interview_date")
    private LocalDate expectedInterviewDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // e.g., "PENDING", "IN_PROGRESS", "COMPLETED"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Changed from ManyToMany to OneToMany for the associative entity JobApplicationQuestion
    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<JobApplicationQuestion> jobApplicationQuestions = new HashSet<>();

    // Helper methods for managing the OneToMany relationship through the associative entity
    public void addJobApplicationQuestion(JobApplicationQuestion jaq) {
        this.jobApplicationQuestions.add(jaq);
        jaq.setJobApplication(this);
    }

    public void removeJobApplicationQuestion(JobApplicationQuestion jaq) {
        this.jobApplicationQuestions.remove(jaq);
        jaq.setJobApplication(null);
    }
}