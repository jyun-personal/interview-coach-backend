package com.interviewcoach.dto;

import com.interviewcoach.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewQuestionDto {
    private UUID id;
    private String questionText;
    private QuestionType questionType;
    private OffsetDateTime createdAt;
    private UUID jobApplicationId; // To link back to job application
    // For displaying user responses and AI feedback on the detail page:
    private String userResponseText;
    private String aiFeedbackText;
    private Integer aiFeedbackScore;
    private UUID userResponseId; // ID of the user's response
    private UUID aiFeedbackId; // ID of the AI feedback
}
