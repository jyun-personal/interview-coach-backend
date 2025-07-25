package com.interviewcoach.model;

import com.interviewcoach.enums.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "interview_questions")
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;  // E.g. "What are your strengths and weaknesses?"

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 50)
    private QuestionType questionType; // BEHAVIORAL, TECHNICAL, SITUATIONAL, etc. It is ok to have null as the question type. A question like "How did you hear about this job?" basically doesn't have a question type

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;  // Timestamp the question was created by AI

    @ManyToMany(mappedBy = "interviewQuestions", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<JobApplication> jobApplications = new HashSet<>(); // Each job application can have multiple interview questions, and each interview question can show up in multiple job applications
}
