package com.interviewcoach.model;

import com.interviewcoach.enums.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "interview_questions")
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 50)
    private QuestionType questionType;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    // Changed from ManyToMany to OneToMany for the associative entity JobApplicationQuestion
    @OneToMany(mappedBy = "interviewQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    private Set<JobApplicationQuestion> jobApplicationQuestions = new HashSet<>();

    // Helper method for managing the OneToMany relationship through the associative entity
    public void addJobApplicationQuestion(JobApplicationQuestion jaq) {
        this.jobApplicationQuestions.add(jaq);
        jaq.setInterviewQuestion(this);
    }
}
