package com.interviewcoach.service;

import com.interviewcoach.dto.InterviewQuestionDto;
import com.interviewcoach.dto.UserResponseSubmitDto;
import com.interviewcoach.enums.QuestionType;

import java.util.List;
import java.util.UUID;

public interface IInterviewQuestionService {
    List<InterviewQuestionDto> generateAndSaveQuestions(
            UUID jobApplicationId,
            Long userId,
            int numberOfQuestions,
            List<QuestionType> questionTypes
    );

    List<InterviewQuestionDto> getQuestionsForJobApplication(UUID jobApplicationId, Long userId);

    InterviewQuestionDto submitUserResponseAndGetFeedback(UUID jobApplicationId, UUID questionId, Long userId, UserResponseSubmitDto userResponseSubmitDto);
}