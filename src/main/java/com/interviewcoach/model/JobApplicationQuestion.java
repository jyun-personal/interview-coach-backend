package com.interviewcoach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "job_application_interview_questions") // The join table is now an entity
@IdClass(JobApplicationQuestionId.class) // Specifies the composite primary key class
public class JobApplicationQuestion implements Serializable { // Serializable for IdClass

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication jobApplication;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_id", nullable = false)
    private InterviewQuestion interviewQuestion;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime addedAt;
}
