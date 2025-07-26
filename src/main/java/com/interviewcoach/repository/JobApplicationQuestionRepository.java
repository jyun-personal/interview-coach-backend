package com.interviewcoach.repository;

import com.interviewcoach.model.JobApplicationQuestion;
import com.interviewcoach.model.JobApplicationQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationQuestionRepository extends JpaRepository<JobApplicationQuestion, JobApplicationQuestionId> {
}
