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
    private JobApplicationQuestionRepository jobApplicationQuestionRepository;
    @Autowired
    private UserResponseRepository userResponseRepository;
    @Autowired
    private AiFeedbackRepository aiFeedbackRepository;
    @Autowired
    private IAIService aiService; // For Pollinations.ai fallback
    @Autowired
    private GeminiService geminiService; // For Gemini API
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<InterviewQuestionDto> generateAndSaveQuestions(
            UUID jobApplicationId,
            Long userId,
            int numberOfQuestions,
            List<QuestionType> questionTypes
    ) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication", "ID", jobApplicationId));

        if (!jobApplication.getUser().getId().equals(userId)) {
            throw new BadRequestException("Access denied: Job application does not belong to user.");
        }

        String prompt = buildPromptForQuestionGeneration(
                jobApplication.getTitle(),
                jobApplication.getDescription(),
                numberOfQuestions,
                questionTypes
        );
        String aiResponse = "";

        // === AI API Integration Logic (Gemini -> Pollinations -> Mock) ===
        try {
            // 1. Try Google Gemini API first
            aiResponse = geminiService.generateText(prompt);
            // If successful, proceed with parsing aiResponse
        } catch (IOException | InterruptedException | BadRequestException e) { // Catch custom exceptions too
            System.err.println("Gemini AI failed: " + e.getMessage() + ". Falling back to Pollinations.ai.");
            // 2. Fallback to Pollinations.ai if Gemini fails
            try {
                aiResponse = aiService.generateTextFromPollinationsAI(prompt);
            } catch (IOException | InterruptedException ex) {
                System.err.println("Pollinations.ai failed: " + ex.getMessage() + ". Falling back to mock data.");
                aiResponse = "MOCK_FALLBACK_QUESTIONS"; // Sentinel value for mock data
            }
        }
        // === End AI API Integration Logic ===

        List<InterviewQuestion> newQuestions = new ArrayList<>();

        if ("MOCK_FALLBACK_QUESTIONS".equals(aiResponse) || aiResponse.isEmpty()) {
            // Use updated generateMockQuestions with parameters
            newQuestions = generateMockQuestions(jobApplication, numberOfQuestions, questionTypes);
        } else {
            newQuestions = parsePollinationsAIQuestions(aiResponse, jobApplication);

            // Filter and limit questions based on requested criteria
            newQuestions = filterAndLimitQuestions(newQuestions, numberOfQuestions, questionTypes);

            if (newQuestions.isEmpty()) {
                System.err.println("AI response did not yield questions matching criteria. Falling back to generic mock questions.");
                newQuestions = generateMockQuestions(jobApplication, numberOfQuestions, questionTypes);
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

        List<InterviewQuestion> questions = interviewQuestionRepository.findByJobApplicationId(jobApplicationId);

        return questions.stream().map(question -> {
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

        UserResponse userResponse = userResponseRepository.findByInterviewQuestionIdAndJobApplicationId(questionId, jobApplicationId)
                .orElse(new UserResponse());
        userResponse.setResponseText(userResponseSubmitDto.getResponseText());
        userResponse.setInterviewQuestion(question);
        userResponse.setJobApplication(jobApplication);
        UserResponse savedUserResponse = userResponseRepository.save(userResponse);

        String feedbackText = "";
        Integer score = null;
        String prompt = buildPromptForFeedbackGeneration(question.getQuestionText(), savedUserResponse.getResponseText());

        // === AI API Integration Logic for Feedback (Gemini -> Pollinations -> Mock) ===
        try {
            // 1. Try Google Gemini API first for feedback
            feedbackText = geminiService.generateText(prompt);
            score = parseScoreFromFeedback(feedbackText); // Extract score from Gemini's response
        } catch (IOException | InterruptedException | BadRequestException e) {
            System.err.println("Gemini AI feedback failed: " + e.getMessage() + ". Falling back to Pollinations.ai.");
            // 2. Fallback to Pollinations.ai if Gemini fails
            try {
                feedbackText = aiService.generateTextFromPollinationsAI(prompt);
                score = parseScoreFromFeedback(feedbackText); // Extract score from Pollinations.ai's response
            } catch (IOException | InterruptedException ex) {
                System.err.println("Pollinations.ai feedback failed: " + ex.getMessage() + ". Falling back to mock data.");
                feedbackText = generateMockFeedback(userResponseSubmitDto.getResponseText());
                score = 6; // Default mock score
            }
        }
        // === End AI API Integration Logic ===

        // Save/Update AiFeedback
        AiFeedback aiFeedback = aiFeedbackRepository.findByUserResponseId(savedUserResponse.getId())
                .orElse(new AiFeedback());
        aiFeedback.setUserResponse(savedUserResponse);
        aiFeedback.setFeedbackText(feedbackText);
        aiFeedback.setScore(score);
        AiFeedback savedAiFeedback = aiFeedbackRepository.save(aiFeedback);

        return mapToDto(question, savedUserResponse, savedAiFeedback);
    }


    // --- Helper Methods for building prompts, etc

    private String buildPromptForQuestionGeneration(
            String jobTitle,
            String jobDescription,
            int numberOfQuestions,
            List<QuestionType> questionTypes
    ) {
        String typesString = questionTypes.stream()
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        return String.format(
                "As a career coach, please generate %d interview questions for a \"%s\" role based on this job description: \"%s\". " +
                        "Focus on the following types: %s. " +
                        "Provide only the questions, each on a new line. Don't include any extra comments, headings, or greetings. " +
                        "Only provide the clean questions in the format specified. [question type] question text ?. For example, [Behavioral] Tell me about a goal you set and reached. How did you achieve it?",
                numberOfQuestions,
                jobTitle,
                jobDescription.substring(0, Math.min(jobDescription.length(), 2000)),
                typesString
        );
    }

    private List<InterviewQuestion> parsePollinationsAIQuestions(String aiResponse, JobApplication jobApplication) {
        List<InterviewQuestion> questions = new ArrayList<>();
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            return questions;
        }

        // Regex to parse "[QuestionType] Question Text" format (with or without punctuation at the end)
        Pattern pattern = Pattern.compile("^\\[(BEHAVIORAL|TECHNICAL|SITUATIONAL|CASE_STUDY)]\\s*(.*)$", Pattern.CASE_INSENSITIVE);
        String[] lines = aiResponse.split("\n");
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) continue;

            Matcher matcher = pattern.matcher(trimmedLine);
            if (matcher.matches()) {
                QuestionType type = QuestionType.valueOf(matcher.group(1).toUpperCase());
                String questionText = matcher.group(2).trim();
                // Ensure the question ends with a question mark
                if (!questionText.endsWith("?") && !questionText.endsWith(".")) {
                    questionText += "?";
                }
                questions.add(InterviewQuestion.builder()
                        .questionText(questionText)
                        .questionType(type)
                        .build());
            } else {
                // Fallback for less structured responses (e.g., old Pollinations.ai or unexpected Gemini format)
                // Try to just take the line as a behavioral question if format isn't strictly matched
                questions.add(InterviewQuestion.builder()
                        .questionText(trimmedLine.replaceAll("^\\d+[.)\\s]*", "").trim()) // Remove leading numbers/dots
                        .questionType(QuestionType.BEHAVIORAL) // Default to behavioral if type not parsed
                        .build());
            }
        }
        return questions;
    }

    private List<InterviewQuestion> generateMockQuestions(
            JobApplication jobApplication,
            int numberOfQuestions,
            List<QuestionType> questionTypes
    ) {
        List<InterviewQuestion> allMockQuestions = new ArrayList<>();

        // Behavioral questions
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Tell me about a time you faced a challenge and how you overcame it.")
                .questionType(QuestionType.BEHAVIORAL)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("How do you handle disagreements within your team regarding technical approaches?")
                .questionType(QuestionType.BEHAVIORAL)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Describe a situation where you had to meet a tight deadline.")
                .questionType(QuestionType.BEHAVIORAL)
                .build());

        // Technical questions
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Explain the concept of polymorphism in object-oriented programming.")
                .questionType(QuestionType.TECHNICAL)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Describe the difference between SQL and NoSQL databases.")
                .questionType(QuestionType.TECHNICAL)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("What are the advantages of using a microservices architecture?")
                .questionType(QuestionType.TECHNICAL)
                .build());

        // Situational questions
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Imagine you discovered a critical bug right before a major product launch. What would you do?")
                .questionType(QuestionType.SITUATIONAL)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("How would you respond if a project deadline was unexpectedly moved up?")
                .questionType(QuestionType.SITUATIONAL)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("What would you do if you disagreed with your manager's technical decision?")
                .questionType(QuestionType.SITUATIONAL)
                .build());

        // Case study questions
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("You have 100 ping pong balls and 100 dollars. How do you maximize your earnings by selling ping pong balls?")
                .questionType(QuestionType.CASE_STUDY)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Your client wants to launch a new streaming service. Walk me through how you would determine the pricing strategy.")
                .questionType(QuestionType.CASE_STUDY)
                .build());
        allMockQuestions.add(InterviewQuestion.builder()
                .questionText("Estimate how many coffee shops are in New York City.")
                .questionType(QuestionType.CASE_STUDY)
                .build());

        // Filter by requested types and limit the number
        List<InterviewQuestion> filteredAndLimitedQuestions = allMockQuestions.stream()
                .filter(q -> questionTypes.contains(q.getQuestionType()))
                .limit(numberOfQuestions)
                .collect(Collectors.toList());

        // If we don't have enough questions of the requested types,
        // add more from common types (BEHAVIORAL, TECHNICAL, SITUATIONAL)
        if (filteredAndLimitedQuestions.size() < numberOfQuestions) {
            List<QuestionType> fallbackTypes = List.of(
                    QuestionType.BEHAVIORAL,
                    QuestionType.TECHNICAL,
                    QuestionType.SITUATIONAL
            );

            long needed = numberOfQuestions - filteredAndLimitedQuestions.size();

            allMockQuestions.stream()
                    .filter(q -> fallbackTypes.contains(q.getQuestionType()) &&
                            !filteredAndLimitedQuestions.contains(q))
                    .limit(needed)
                    .forEach(filteredAndLimitedQuestions::add);
        }

        // If still not enough questions, add any remaining questions
        while (filteredAndLimitedQuestions.size() < numberOfQuestions &&
                filteredAndLimitedQuestions.size() < allMockQuestions.size()) {
            Optional<InterviewQuestion> randomQuestion = allMockQuestions.stream()
                    .filter(q -> !filteredAndLimitedQuestions.contains(q))
                    .findAny();
            randomQuestion.ifPresent(filteredAndLimitedQuestions::add);
        }

        return filteredAndLimitedQuestions;
    }

    private String buildPromptForFeedbackGeneration(String questionText, String userResponseText) {
        return String.format("As a career coach, evaluate the following user response to the question: '%s'. " +
                        "User's response: '%s'. Provide constructive feedback and a suggested score out of 10. " +
                        "Focus on clarity, completeness, relevance, and communication style. " +
                        "Acknowledge what the user did well in their response first, and then start giving suggestions. " +
                        "Make your feedback one or more paragraphs of plain text, with no special characters like the asterisk and no need to divide into sections. " +
                        "Format it as a simple script that a career coach could take and read and make it sound like the way a person would naturally talk. " +
                        "In addition to suggestions, also provide examples of best practices and tips to answer questions like this in the future. " +
                        "If possible make reference to common interview response techniques such as the STAR, CAR, PAR and SOAR methods. " +
                        "Avoid making each paragraph longer than 5 sentences for better readability. " +
                        "To emphasize an important word, make it all capitalized instead of surrounding it with asterisks. " +
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

    private int parseScoreFromFeedback(String feedbackText) {
        try {
            Pattern pattern = Pattern.compile("Score: (\\d+)/10");
            Matcher matcher = pattern.matcher(feedbackText);

            if (matcher.find()) {
                int score = Integer.parseInt(matcher.group(1));
                return Math.max(1, Math.min(10, score)); // Ensure score is between 1-10
            }
        } catch (NumberFormatException e) {
            System.err.println("Could not parse score from feedback: " + e.getMessage());
        }
        return 6 + (int) (Math.random() * 4); // Fallback to a random score between 6-9
    }

    private List<InterviewQuestion> filterAndLimitQuestions(
            List<InterviewQuestion> questions,
            int numberOfQuestions,
            List<QuestionType> questionTypes
    ) {
        return questions.stream()
                .filter(q -> questionTypes.contains(q.getQuestionType()))
                .limit(numberOfQuestions)
                .collect(Collectors.toList());
    }
}