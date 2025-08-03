package com.interviewcoach.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.interviewcoach.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

// Service class for interacting with the Google Gemini API (Google AI Studio).
@Service
public class GeminiService {

    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    // Inject the Gemini API environment variables
    @Value("${google.api.key}")
    private String apiKey;

    // Inject the model name environment variables
    // We can configure different models (e.g., gemini-2.5-flash, gemini-2.5-pro)
    // The default value 'gemini-2.5-flash-lite' will be used if the property is not set
    @Value("${gemini.model:gemini-2.5-flash-lite}")
    private String modelName;

    // The Gemini API client instance. It's built once when the service is initialized.
    private Client geminiClient;

    // Initializes the GeminiService.
    public void init() {
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("ERROR: GOOGLE_API_KEY is not configured. Gemini API calls will fail.");
            throw new IllegalArgumentException("GOOGLE_API_KEY environment variable or property not set.");
        }
        // Build the Gemini API client using the injected API key
        this.geminiClient = Client.builder().apiKey(apiKey).build();
        logger.info("GeminiService initialized with model: {}", modelName);
    }

    // Generates text content using the configured Gemini model.
    public String generateText(String prompt) throws IOException, InterruptedException {
        // Ensure the client is initialized before making calls
        if (this.geminiClient == null) {
            init(); // Re-initialize if for some reason it's null (shouldn't happen with proper Spring lifecycle)
        }

        logger.info("Attempting to connect to Gemini API with model: {} and prompt (first 100 chars): {}", modelName, prompt.substring(0, Math.min(prompt.length(), 100)));

        try {
            // Make the content generation API call using the Google Gen AI SDK
            GenerateContentResponse response = geminiClient.models.generateContent(modelName, prompt, null);

            // Extract the text content from the response
            String geminiResponseText = response.text();
            logger.info("Successfully received response from Gemini API.");
            return geminiResponseText;

        } catch (Exception e) {
            // Log the error for debugging purposes
            logger.error("An error occurred during Gemini API call: {}", e.getMessage(), e);

            // Re-throw as a custom BadRequestException or a more specific service exception
            // This allows our GlobalExceptionHandler to catch and format the error consistently.
            if (e.getMessage().contains("API_KEY_INVALID") || e.getMessage().contains("invalid API key")) {
                throw new BadRequestException("Gemini API Error: Your API Key might be invalid. Please check your GOOGLE_API_KEY configuration.");
            }
            if (e.getMessage().contains("RESOURCE_EXHAUSTED") || e.getMessage().contains("rate limit")) {
                throw new BadRequestException("Gemini API Error: Rate limit exceeded. Please wait a moment and try again.");
            }
            throw new IOException("Failed to get response from Gemini API: " + e.getMessage(), e);
        }
    }
}