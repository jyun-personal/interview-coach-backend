package com.interviewcoach.repository;

import com.interviewcoach.model.AiFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AiFeedbackRepository extends JpaRepository<AiFeedback, UUID> {
    Optional<AiFeedback> findByUserResponseId(UUID userResponseId);
}