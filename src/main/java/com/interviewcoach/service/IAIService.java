package com.interviewcoach.service;

import java.io.IOException;

public interface IAIService {
    String generateTextFromPollinationsAI(String prompt) throws IOException, InterruptedException;

    String generateTextFromGeminiAI(String prompt, String apiKey) throws IOException, InterruptedException;
}