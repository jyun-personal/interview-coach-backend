package com.interviewcoach.controller;

import com.interviewcoach.dto.InterviewQuestionDto;
import com.interviewcoach.dto.UserResponseSubmitDto;
import com.interviewcoach.service.IInterviewQuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-applications/{jobApplicationId}/questions") // base path
public class InterviewQuestionController {

    @Autowired
    private IInterviewQuestionService interviewQuestionService;

    @PostMapping("/generate")
    public ResponseEntity<List<InterviewQuestionDto>> generateQuestions(@PathVariable UUID jobApplicationId,
                                                                        @RequestHeader("X-User-ID") Long userId) {
        List<InterviewQuestionDto> questions = interviewQuestionService.generateAndSaveQuestions(jobApplicationId, userId);
        return new ResponseEntity<>(questions, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestionDto>> getQuestionsForJobApplication(@PathVariable UUID jobApplicationId,
                                                                                    @RequestHeader("X-User-ID") Long userId) {
        List<InterviewQuestionDto> questions = interviewQuestionService.getQuestionsForJobApplication(jobApplicationId, userId);
        return ResponseEntity.ok(questions);
    }

    // Endpoint for user to submit their response and get feedback
    @PatchMapping("/{questionId}/respond")
    public ResponseEntity<InterviewQuestionDto> submitUserResponse(@PathVariable UUID jobApplicationId,
                                                                   @PathVariable UUID questionId,
                                                                   @RequestHeader("X-User-ID") Long userId,
                                                                   @Valid @RequestBody UserResponseSubmitDto userResponseSubmitDto) {
        InterviewQuestionDto updatedQuestion = interviewQuestionService.submitUserResponseAndGetFeedback(jobApplicationId, questionId, userId, userResponseSubmitDto);
        return ResponseEntity.ok(updatedQuestion);
    }
}