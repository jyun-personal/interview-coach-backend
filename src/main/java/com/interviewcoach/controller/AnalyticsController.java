package com.interviewcoach.controller;

import com.interviewcoach.dto.UserAnalyticsDto;
import com.interviewcoach.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("/user")
    public ResponseEntity<UserAnalyticsDto> getUserAnalytics(@RequestHeader("X-User-ID") Long userId) {
        UserAnalyticsDto analytics = analyticsService.getUserAnalytics(userId);
        return ResponseEntity.ok(analytics);
    }
}