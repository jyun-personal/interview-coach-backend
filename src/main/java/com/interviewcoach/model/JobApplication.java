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

    @Column(name = "application_date", nullable = false) // Made nullable false based on logic of setting on creation
    @CreationTimestamp // Set automatically when persisted
    private OffsetDateTime applicationDate; // Changed to OffsetDateTime for consistency

    @Column(name = "expected_interview_date")
    private LocalDate expectedInterviewDate;

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
