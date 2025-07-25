package com.interviewcoach.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String title;  // title of the job: e.g "Software Engineer"

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // short description of the job, user provided on the frontend by pasting into a text box, should contain basic sections such as job location, job requirements, job responsibilities, company culture, etc

    @Column(name = "application_date")
    private LocalDate applicationDate;  // date when the job application was submitted by the user on Indeed

    @Column(name = "expected_interview_date")
    private LocalDate expectedInterviewDate;  // date when the user is expected to be interviewed. This should be a date in the future based on the job application date and at the time that the user is practicing for the interview with this application

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // One user can have many job applications but one job application can only be created by one user

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "job_application_interview_questions",
            joinColumns = @JoinColumn(name = "job_application_id"),
            inverseJoinColumns = @JoinColumn(name = "interview_question_id")
    )
    @Builder.Default
    private Set<InterviewQuestion> interviewQuestions = new HashSet<>();  // One job application can have many interview questions and the same interview question can show up in multiple job applications
}
