package com.interviewcoach.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

//@Data
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplicationQuestion that = (JobApplicationQuestion) o;
        return Objects.equals(jobApplication != null ? jobApplication.getId() : null,
                that.jobApplication != null ? that.jobApplication.getId() : null) &&
                Objects.equals(interviewQuestion != null ? interviewQuestion.getId() : null,
                        that.interviewQuestion != null ? that.interviewQuestion.getId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                jobApplication != null ? jobApplication.getId() : null,
                interviewQuestion != null ? interviewQuestion.getId() : null
        );
    }

}
