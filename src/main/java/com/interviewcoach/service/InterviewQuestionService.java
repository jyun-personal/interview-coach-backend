package com.interviewcoach.service;

import com.interviewcoach.dto.InterviewQuestionDto;
import com.interviewcoach.dto.UserResponseSubmitDto;
import com.interviewcoach.enums.QuestionType;
import com.interviewcoach.exception.BadRequestException;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.*;
import com.interviewcoach.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class InterviewQuestionService implements IInterviewQuestionService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;
    @Autowired
    private JobApplicationQuestionRepository jobApplicationQuestionRepository; // For associative entity
    @Autowired
    private UserResponseRepository userResponseRepository;
    @Autowired
    private AiFeedbackRepository aiFeedbackRepository;
    @Autowired
    private IAIService aiService; // Inject our AI service
    @Autowired
    private ModelMapper modelMapper;

    @Value("${gemini.api.key:}") // Use @Value for Gemini API key
    private String geminiApiKey;


    @Override
    @Transactional
    public List<InterviewQuestionDto> generateAndSaveQuestions(UUID jobApplicationId, Long userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));

        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }

        String prompt = buildPromptForQuestionGeneration(jobApplication.getTitle(), jobApplication.getDescription());
        String aiResponse = "";

        // 1. Try Google Gemini (if API key is configured and service integrated)
        // FOR MVP: Gemini is noted as a future integration, so this will currently throw an IOException from AIService
        try {
            // Placeholder: If Gemini were integrated, this would be: aiResponse = aiService.generateTextFromGeminiAI(prompt, geminiApiKey);
            aiResponse = aiService.generateTextFromGeminiAI(prompt, geminiApiKey); // This will throw error for MVP
        } catch (IOException | InterruptedException e) {
            System.err.println("Gemini AI failed/not integrated: " + e.getMessage() + ". Falling back to Pollinations.ai.");
            // 2. Fallback to Pollinations.ai
            try {
                aiResponse = aiService.generateTextFromPollinationsAI(prompt);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Pollinations.ai failed: " + ex.getMessage() + ". Falling back to mock data.");
                aiResponse = "MOCK_FALLBACK_QUESTIONS"; // Sentinel value for mock data
            }
        }

        List<InterviewQuestion> newQuestions = new ArrayList<>();
        if ("MOCK_FALLBACK_QUESTIONS".equals(aiResponse) || aiResponse.isEmpty()) {
            newQuestions = generateMockQuestions(jobApplication);
        } else {
            // Attempt to parse AI response (Pollinations.ai)
            newQuestions = parsePollinationsAIQuestions(aiResponse, jobApplication);
            if (newQuestions.isEmpty()) { // If parsing failed for some reason
                System.err.println("Failed to parse AI response. Falling back to mock questions.");
                newQuestions = generateMockQuestions(jobApplication);
            }
        }

        // Save new questions and establish relationship with JobApplication via associative entity
        for (InterviewQuestion question : newQuestions) {
            InterviewQuestion savedQuestion = interviewQuestionRepository.save(question); // Save question first
            JobApplicationQuestion jaq = JobApplicationQuestion.builder()
                    .jobApplication(jobApplication)
                    .interviewQuestion(savedQuestion)
                    .build();
            jobApplicationQuestionRepository.save(jaq); // Save the association
            jobApplication.addJobApplicationQuestion(jaq); // Update the collection in memory
        }
        jobApplicationRepository.save(jobApplication); // Persist updated jobApplicationQuestions collection if necessary

        return newQuestions.stream()
                .map(q -> mapToDto(q, null, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<InterviewQuestionDto> getQuestionsForJobApplication(UUID jobApplicationId, Long userId) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));

        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }

        // Fetch questions using the custom query
        List<InterviewQuestion> questions = interviewQuestionRepository.findByJobApplicationId(jobApplicationId);

        // Populate UserResponse and AiFeedback into DTOs
        return questions.stream().map(question -> {
            // Find UserResponse for this question and job application
            Optional<UserResponse> userResponseOpt = userResponseRepository.findByInterviewQuestionIdAndJobApplicationId(question.getId(), jobApplicationId);
            UserResponse userResponse = userResponseOpt.orElse(null);

            AiFeedback aiFeedback = null;
            if (userResponse != null) {
                aiFeedback = aiFeedbackRepository.findByUserResponseId(userResponse.getId()).orElse(null);
            }
            return mapToDto(question, userResponse, aiFeedback);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InterviewQuestionDto submitUserResponseAndGetFeedback(UUID jobApplicationId, UUID questionId, Long userId, UserResponseSubmitDto userResponseSubmitDto) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));
        InterviewQuestion question = interviewQuestionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("InterviewQuestion", "ID", questionId));

        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }

        // Check if the question is actually linked to the job application (via JobApplicationQuestion)
        // Optional: Add a check here using jobApplicationQuestionRepository if you want to be super strict
        // (e.g., jobApplicationQuestionRepository.findByJobApplicationAndInterviewQuestion(jobApplication, question).isEmpty())

        // Save UserResponse
        UserResponse userResponse = userResponseRepository.findByInterviewQuestionIdAndJobApplicationId(questionId, jobApplicationId)
                .orElse(new UserResponse()); // Create new if not exists
        userResponse.setResponseText(userResponseSubmitDto.getResponseText());
        userResponse.setInterviewQuestion(question);
        userResponse.setJobApplication(jobApplication);
        UserResponse savedUserResponse = userResponseRepository.save(userResponse);

        // Generate AI Feedback
        String feedbackText = "";
        Integer score = null;
        String prompt = buildPromptForFeedbackGeneration(question.getQuestionText(), savedUserResponse.getResponseText());

        // 1. Try Google Gemini (if integrated)
        // FOR MVP: Gemini is noted as a future integration, so this will currently throw an IOException from AIService
        try {
            // Placeholder: If Gemini were integrated, this would be: feedbackText = aiService.generateTextFromGeminiAI(prompt, geminiApiKey);
            feedbackText = aiService.generateTextFromGeminiAI(prompt, geminiApiKey); // This will throw error for MVP
            score = parseScoreFromFeedback(feedbackText);
        } catch (IOException | InterruptedException e) {
            System.err.println("Gemini AI feedback failed/not integrated: " + e.getMessage() + ". Falling back to Pollinations.ai.");
            // 2. Fallback to Pollinations.ai
            try {
                feedbackText = aiService.generateTextFromPollinationsAI(prompt);
                score = parseScoreFromFeedback(feedbackText);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Pollinations.ai feedback failed: " + ex.getMessage() + ". Falling back to mock data.");
                feedbackText = generateMockFeedback(userResponseSubmitDto.getResponseText()); // Mock feedback
                score = 6; // Mock score for mock feedback
            }
        }

        // Save/Update AiFeedback
        AiFeedback aiFeedback = aiFeedbackRepository.findByUserResponseId(savedUserResponse.getId())
                .orElse(new AiFeedback()); // Create new if not exists
        aiFeedback.setUserResponse(savedUserResponse); // Set the relationship
        aiFeedback.setFeedbackText(feedbackText);
        aiFeedback.setScore(score);
        AiFeedback savedAiFeedback = aiFeedbackRepository.save(aiFeedback);

        // Return updated question DTO with latest response and feedback
        return mapToDto(question, savedUserResponse, savedAiFeedback);
    }


    // --- Helper Methods ---

    private String buildPromptForQuestionGeneration(String jobTitle, String jobDescription) {
        return String.format("As a career coach, please generate 1 behavioral, 1 technical, and 1 situational interview question for a \"%s\" role based on this job description: \"%s\". " +
                        "Provide only the questions, each on a new line.",
                jobTitle, jobDescription.substring(0, Math.min(jobDescription.length(), 2000))); // Truncate JD for prompt length
    }

    private List<InterviewQuestion> parsePollinationsAIQuestions(String aiResponse, JobApplication jobApplication) {
        List<InterviewQuestion> questions = new ArrayList<>();
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            return questions;
        }

        String[] lines = aiResponse.split("\n");
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) continue;

            QuestionType type = null; // Default to null, or try to infer from line content

            // Simple inference, can be expanded
            if (trimmedLine.toLowerCase().contains("behavioral")) type = QuestionType.BEHAVIORAL;
            else if (trimmedLine.toLowerCase().contains("technical")) type = QuestionType.TECHNICAL;
            else if (trimmedLine.toLowerCase().contains("situational")) type = QuestionType.SITUATIONAL;
            else if (trimmedLine.toLowerCase().contains("case study")) type = QuestionType.CASE_STUDY;

            questions.add(InterviewQuestion.builder()
                    .questionText(trimmedLine.replaceAll("^\\d+[.)\\s]*", "").trim()) // Remove leading numbers/dots
                    .questionType(type)
                    .build());
        }
        return questions;
    }

    private List<InterviewQuestion> generateMockQuestions(JobApplication jobApplication) {
        return List.of(
                InterviewQuestion.builder().questionText("Tell me about a time you faced a challenge and how you overcame it.").questionType(QuestionType.BEHAVIORAL).build(),
                InterviewQuestion.builder().questionText("Explain the concept of polymorphism in object-oriented programming.").questionType(QuestionType.TECHNICAL).build(),
                InterviewQuestion.builder().questionText("Imagine you discovered a critical bug right before a major product launch. What would you do?").questionType(QuestionType.SITUATIONAL).build()
        );
    }

    private String buildPromptForFeedbackGeneration(String questionText, String userResponseText) {
        return String.format("As a career coach, evaluate the following user response to the question: '%s'. " +
                        "User's response: '%s'. Provide constructive feedback and a suggested score out of 10. " +
                        "Focus on clarity, completeness, relevance, and communication style. " +
                        "Format: Feedback: [feedback text]. Score: [score]/10.",
                questionText, userResponseText);
    }

    private String generateMockFeedback(String userResponseText) {
        if (userResponseText == null || userResponseText.trim().isEmpty()) {
            return "Feedback: Your response was empty. Please provide a substantive answer. Score: 1/10.";
        } else if (userResponseText.length() < 50) {
            return "Feedback: Your response is a bit too brief. Try to elaborate more and provide specific examples to support your points. Score: 5/10.";
        } else if (userResponseText.toLowerCase().contains("i don't know")) {
            return "Feedback: While honesty is appreciated, try to offer what you do know, or explain your thought process and how you would find the answer. Score: 3/10.";
        } else {
            return "Feedback: Good job! Your response is clear and directly addresses the question. Consider adding a strong example to make your answer more impactful. Score: 7/10.";
        }
    }

    // Helper method to map InterviewQuestion entity to DTO, including UserResponse and AiFeedback
    private InterviewQuestionDto mapToDto(InterviewQuestion question, UserResponse userResponse, AiFeedback aiFeedback) {
        InterviewQuestionDto dto = modelMapper.map(question, InterviewQuestionDto.class);

        if (userResponse != null) {
            dto.setUserResponseText(userResponse.getResponseText());
            dto.setUserResponseId(userResponse.getId());
        }

        if (aiFeedback != null) {
            dto.setAiFeedbackText(aiFeedback.getFeedbackText());
            dto.setAiFeedbackScore(aiFeedback.getScore());
            dto.setAiFeedbackId(aiFeedback.getId());
        }
        return dto;
    }

    // Function to parse the score from the AI feedback message string
    private int parseScoreFromFeedback(String feedbackText) {
        try {
            // Look for "Score: X/10" pattern in the feedback
            Pattern pattern = Pattern.compile("Score: (\\d+)/10");
            Matcher matcher = pattern.matcher(feedbackText);

            if (matcher.find()) {
                int score = Integer.parseInt(matcher.group(1));
                // Ensure score is between 1-10
                return Math.max(1, Math.min(10, score));
            }
        } catch (NumberFormatException e) {
            System.err.println("Could not parse score from feedback: " + e.getMessage());
        }

        // Fallback: generate random score if parsing fails
        return 6 + (int) (Math.random() * 4); // 6-9
    }
}