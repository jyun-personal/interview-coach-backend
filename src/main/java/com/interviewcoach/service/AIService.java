package com.interviewcoach.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class AIService implements IAIService {

    private static final String POLLINATIONS_AI_ENDPOINT = "https://text.pollinations.ai/prompt/";
    private final HttpClient httpClient;

    public AIService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // This method is for calling Pollinations.ai (plain text response)
    @Override
    public String generateTextFromPollinationsAI(String prompt) throws IOException, InterruptedException {
        String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(POLLINATIONS_AI_ENDPOINT + encodedPrompt))
                .GET()
                .timeout(java.time.Duration.ofSeconds(15)) // Set a timeout
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.err.println("Pollinations.ai API call failed with status code: " + response.statusCode() + " and body: " + response.body());
            throw new IOException("Pollinations.ai API call failed with status: " + response.statusCode());
        }
    }

    // Placeholder for Google Gemini API integration (Future/Bonus)
    // For a real Gemini integration, we need to use Google's client libraries or implement complex HTTP POST requests
    // with authentication. This is just a conceptual outline.
    @Override
    public String generateTextFromGeminiAI(String prompt, String apiKey) throws IOException, InterruptedException {
        // Dummy implementation. Replace with actual Gemini API call.
        // Google Gemini usually requires POST requests to a specific endpoint like
        // https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=YOUR_API_KEY
        // with a JSON body {"contents": [{"parts": [{"text": "your prompt"}]}]}
        // This is complex and out of scope for a basic MVP if not using their client libraries.
        System.out.println("Attempting to call Gemini AI (mock/placeholder implementation): " + prompt);
        // For MVP, we'll just fall back directly to Pollinations.ai or mock if this is invoked.
        throw new IOException("Gemini API not fully integrated for MVP. Falling back.");
    }
}