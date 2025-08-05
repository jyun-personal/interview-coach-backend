package com.interviewcoach.repository;

import com.interviewcoach.model.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, UUID> {
    Optional<UserResponse> findByInterviewQuestionIdAndJobApplicationId(UUID questionId, UUID jobApplicationId);

    List<UserResponse> findByJobApplicationId(UUID jobApplicationId);
    
    @Query("SELECT ur FROM UserResponse ur WHERE ur.jobApplication.user.id = :userId")
    List<UserResponse> findByJobApplicationUserId(@Param("userId") Long userId);
}