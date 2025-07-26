package com.interviewcoach.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationQuestionId implements Serializable {
    private UUID jobApplication; // Refers to the field name in JobApplicationQuestion entity
    private UUID interviewQuestion; // Refers to the field name in JobApplicationQuestion entity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplicationQuestionId that = (JobApplicationQuestionId) o;
        return Objects.equals(jobApplication, that.jobApplication) &&
                Objects.equals(interviewQuestion, that.interviewQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobApplication, interviewQuestion);
    }
}