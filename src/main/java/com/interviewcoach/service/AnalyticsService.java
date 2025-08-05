package com.interviewcoach.service;

import com.interviewcoach.dto.UserAnalyticsDto;
import com.interviewcoach.enums.QuestionType;
import com.interviewcoach.exception.ResourceNotFoundException;
import com.interviewcoach.model.*;
import com.interviewcoach.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private UserResponseRepository userResponseRepository;
    
    @Autowired
    private AiFeedbackRepository aiFeedbackRepository;
    
    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;

    public UserAnalyticsDto getUserAnalytics(Long userId) {
        // Verify user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        
        // Get all job applications for user
        List<JobApplication> jobApplications = jobApplicationRepository.findByUserId(userId);
        
        // Get all user responses
        List<UserResponse> userResponses = userResponseRepository.findByJobApplicationUserId(userId);
        
        // Get all AI feedback for scores
        List<AiFeedback> feedbacks = userResponses.stream()
                .map(response -> aiFeedbackRepository.findByUserResponseId(response.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        
        // Calculate metrics
        UserAnalyticsDto analytics = UserAnalyticsDto.builder()
                .totalJobApplications(jobApplications.size())
                .upcomingInterviews(countUpcomingInterviews(jobApplications))
                .totalPracticeSessions(countUniquePracticeSessions(userResponses))
                .totalQuestionsAnswered(userResponses.size())
                .averageScore(calculateAverageScore(feedbacks))
                .highestScore(findHighestScore(feedbacks))
                .lowestScore(findLowestScore(feedbacks))
                .improvementRate(calculateImprovementRate(feedbacks))
                .applicationsPending(countByStatus(jobApplications, "PENDING"))
                .applicationsInProgress(countByStatus(jobApplications, "IN_PROGRESS"))
                .applicationsCompleted(countByStatus(jobApplications, "COMPLETED"))
                .currentStreak(calculateCurrentStreak(userResponses))
                .longestStreak(calculateLongestStreak(userResponses))
                .lastPracticeDate(findLastPracticeDate(userResponses))
                .practiceSessionsThisWeek(countSessionsInPeriod(userResponses, 7))
                .practiceSessionsThisMonth(countSessionsInPeriod(userResponses, 30))
                .behavioralAvgScore(calculateAvgScoreByType(userResponses, feedbacks, QuestionType.BEHAVIORAL))
                .technicalAvgScore(calculateAvgScoreByType(userResponses, feedbacks, QuestionType.TECHNICAL))
                .situationalAvgScore(calculateAvgScoreByType(userResponses, feedbacks, QuestionType.SITUATIONAL))
                .mostRecentJobApplication(findMostRecentJobTitle(jobApplications))
                .mostRecentApplicationDate(findMostRecentApplicationDate(jobApplications))
                .build();
        
        return analytics;
    }
    
    private int countUpcomingInterviews(List<JobApplication> jobApplications) {
        LocalDate today = LocalDate.now();
        return (int) jobApplications.stream()
                .filter(app -> app.getExpectedInterviewDate() != null)
                .filter(app -> app.getExpectedInterviewDate().isAfter(today))
                .count();
    }
    
    private int countUniquePracticeSessions(List<UserResponse> responses) {
        // Count unique job application IDs (each represents a practice session)
        return (int) responses.stream()
                .map(UserResponse::getJobApplication)
                .map(JobApplication::getId)
                .distinct()
                .count();
    }
    
    private Double calculateAverageScore(List<AiFeedback> feedbacks) {
        List<Integer> scores = feedbacks.stream()
                .map(AiFeedback::getScore)
                .filter(score -> score != null)
                .collect(Collectors.toList());
        
        if (scores.isEmpty()) return 0.0;
        
        return scores.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
    
    private Integer findHighestScore(List<AiFeedback> feedbacks) {
        return feedbacks.stream()
                .map(AiFeedback::getScore)
                .filter(score -> score != null)
                .max(Integer::compareTo)
                .orElse(0);
    }
    
    private Integer findLowestScore(List<AiFeedback> feedbacks) {
        return feedbacks.stream()
                .map(AiFeedback::getScore)
                .filter(score -> score != null)
                .min(Integer::compareTo)
                .orElse(0);
    }
    
    private Double calculateImprovementRate(List<AiFeedback> feedbacks) {
        List<AiFeedback> sortedFeedbacks = feedbacks.stream()
                .filter(f -> f.getScore() != null)
                .sorted((f1, f2) -> f1.getCreatedAt().compareTo(f2.getCreatedAt()))
                .collect(Collectors.toList());
        
        if (sortedFeedbacks.size() < 2) return 0.0;
        
        // Compare average of first 3 vs last 3 scores
        int firstCount = Math.min(3, sortedFeedbacks.size() / 2);
        int lastCount = Math.min(3, sortedFeedbacks.size() / 2);
        
        double firstAvg = sortedFeedbacks.stream()
                .limit(firstCount)
                .mapToInt(AiFeedback::getScore)
                .average()
                .orElse(0.0);
        
        double lastAvg = sortedFeedbacks.stream()
                .skip(sortedFeedbacks.size() - lastCount)
                .mapToInt(AiFeedback::getScore)
                .average()
                .orElse(0.0);
        
        if (firstAvg == 0) return 0.0;
        
        return ((lastAvg - firstAvg) / firstAvg) * 100;
    }
    
    private int countByStatus(List<JobApplication> jobApplications, String status) {
        return (int) jobApplications.stream()
                .filter(app -> status.equalsIgnoreCase(app.getStatus()))
                .count();
    }
    
    private int calculateCurrentStreak(List<UserResponse> responses) {
        if (responses.isEmpty()) return 0;
        
        List<LocalDate> practiceDates = responses.stream()
                .map(UserResponse::getCreatedAt)
                .map(dateTime -> dateTime.toLocalDate())
                .distinct()
                .sorted((d1, d2) -> d2.compareTo(d1)) // Sort descending
                .collect(Collectors.toList());
        
        if (practiceDates.isEmpty()) return 0;
        
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        
        // Check if practiced today or yesterday
        if (!practiceDates.get(0).equals(today) && !practiceDates.get(0).equals(yesterday)) {
            return 0; // Streak broken
        }
        
        int streak = 1;
        LocalDate currentDate = practiceDates.get(0);
        
        for (int i = 1; i < practiceDates.size(); i++) {
            LocalDate nextDate = practiceDates.get(i);
            if (currentDate.minusDays(1).equals(nextDate)) {
                streak++;
                currentDate = nextDate;
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    private int calculateLongestStreak(List<UserResponse> responses) {
        // Similar to current streak but tracks the maximum
        // For simplicity, returning current streak for now
        return calculateCurrentStreak(responses);
    }
    
    private OffsetDateTime findLastPracticeDate(List<UserResponse> responses) {
        return responses.stream()
                .map(UserResponse::getCreatedAt)
                .max(OffsetDateTime::compareTo)
                .orElse(null);
    }
    
    private int countSessionsInPeriod(List<UserResponse> responses, int days) {
        OffsetDateTime cutoffDate = OffsetDateTime.now().minusDays(days);
        
        return (int) responses.stream()
                .filter(response -> response.getCreatedAt().isAfter(cutoffDate))
                .map(UserResponse::getJobApplication)
                .map(JobApplication::getId)
                .distinct()
                .count();
    }
    
    private Double calculateAvgScoreByType(List<UserResponse> responses, 
                                           List<AiFeedback> feedbacks, 
                                           QuestionType questionType) {
        List<Integer> scores = responses.stream()
                .filter(response -> response.getInterviewQuestion().getQuestionType() == questionType)
                .map(response -> feedbacks.stream()
                        .filter(f -> f.getUserResponse().getId().equals(response.getId()))
                        .findFirst()
                        .map(AiFeedback::getScore)
                        .orElse(null))
                .filter(score -> score != null)
                .collect(Collectors.toList());
        
        if (scores.isEmpty()) return 0.0;
        
        return scores.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
    
    private String findMostRecentJobTitle(List<JobApplication> jobApplications) {
        return jobApplications.stream()
                .max((app1, app2) -> app1.getApplicationDate().compareTo(app2.getApplicationDate()))
                .map(JobApplication::getTitle)
                .orElse("No applications yet");
    }
    
    private OffsetDateTime findMostRecentApplicationDate(List<JobApplication> jobApplications) {
        return jobApplications.stream()
                .map(JobApplication::getApplicationDate)
                .max(OffsetDateTime::compareTo)
                .orElse(null);
    }
}