package com.interviewcoach.repository;

import com.interviewcoach.model.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, UUID> {
    // Custom query to get questions associated with a specific job application
    // Using JPQL with explicit join for the associative entity
    @Query("SELECT iq FROM InterviewQuestion iq JOIN JobApplicationQuestion jaq ON iq.id = jaq.interviewQuestion.id WHERE jaq.jobApplication.id = :jobApplicationId ORDER BY jaq.addedAt ASC")
    List<InterviewQuestion> findByJobApplicationId(UUID jobApplicationId);

    // Find questions by a set of IDs
    List<InterviewQuestion> findAllByIdIn(Set<UUID> ids);
}
