package com.interviewcoach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnalyticsDto {
    
    // Basic counts
    private int totalJobApplications;
    private int upcomingInterviews;
    private int totalPracticeSessions;
    private int totalQuestionsAnswered;
    
    // Performance metrics
    private Double averageScore;
    private Integer highestScore;
    private Integer lowestScore;
    private Double improvementRate; // Percentage improvement over time
    
    // Status breakdown
    private int applicationsPending;
    private int applicationsInProgress;
    private int applicationsCompleted;
    
    // Time-based metrics
    private int currentStreak; // Days of consecutive practice
    private int longestStreak;
    private OffsetDateTime lastPracticeDate;
    private int practiceSessionsThisWeek;
    private int practiceSessionsThisMonth;
    
    // Question type performance
    private Double behavioralAvgScore;
    private Double technicalAvgScore;
    private Double situationalAvgScore;
    
    // Recent activity
    private String mostRecentJobApplication;
    private OffsetDateTime mostRecentApplicationDate;
}