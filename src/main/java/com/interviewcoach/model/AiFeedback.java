package com.interviewcoach.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private UUID id; // Matches UserResponse.id for shared primary key

    @OneToOne
    @MapsId
    // Maps the primary key of the owning entity (AiFeedback) to the primary key of the associated entity (UserResponse)
    @JoinColumn(name = "user_response_id", nullable = false) // user_response_id column will be the PK and FK
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