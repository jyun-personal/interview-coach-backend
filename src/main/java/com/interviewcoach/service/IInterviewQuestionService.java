package com.interviewcoach.service;

import com.interviewcoach.dto.InterviewQuestionDto;
import com.interviewcoach.dto.UserResponseSubmitDto;

import java.util.List;
import java.util.UUID;

public interface IInterviewQuestionService {
    List<InterviewQuestionDto> generateAndSaveQuestions(UUID jobApplicationId, Long userId);

    List<InterviewQuestionDto> getQuestionsForJobApplication(UUID jobApplicationId, Long userId);

    InterviewQuestionDto submitUserResponseAndGetFeedback(UUID jobApplicationId, UUID questionId, Long userId, UserResponseSubmitDto userResponseSubmitDto);
}