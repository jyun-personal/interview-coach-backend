package com.interviewcoach.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ai_feedbacks")
public class AiFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_response_id", nullable = false, unique = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserResponse userResponse;

    @Column(name = "feedback_text", columnDefinition = "TEXT", nullable = false)
    private String feedbackText;

    @Min(1)
    @Max(10)
    @Column(name = "score")
    private Integer score;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    // Removed redundant 'interviewQuestion' field as it can be accessed via userResponse.getInterviewQuestion()
}