package com.interviewcoach.repository;

import com.interviewcoach.model.JobApplication;
import com.interviewcoach.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    List<JobApplication> findByUserId(Long userId); // Find all job applications for a specific user

    Page<JobApplication> findAllByUser(User user, Pageable pageable);

    // Custom JPQL Query 1: Count job applications by status for a specific user
    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.user.id = :userId AND ja.status = :status")
    Long countByUserIdAndStatus(Long userId, String status);

    // Custom JPQL Query 2: Find JobApplications for a user that have an expected interview date in the future. This can be used by the frontend to display upcoming interviews to the user.
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId AND ja.expectedInterviewDate > CURRENT_DATE ORDER BY ja.expectedInterviewDate ASC")
    List<JobApplication> findUpcomingJobApplicationsByUserId(Long userId);
}
