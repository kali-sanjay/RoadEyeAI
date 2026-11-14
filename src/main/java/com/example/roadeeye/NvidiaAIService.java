package com.example.roadeeye;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NvidiaAIService {

    @Value("${nvidia.api.url}")
    private String apiUrl;

    @Value("${nvidia.api.model}")
    private String model;

    @Value("${NVIDIA_API_KEY}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    public String analyzeRoadImage(MultipartFile image) throws Exception {

        String base64Image = Base64.getEncoder()
                .encodeToString(image.getBytes());

        String mimeType = image.getContentType();

        if (mimeType == null || !mimeType.startsWith("image/")) {
            throw new IllegalArgumentException(
                    "Please upload a valid road image.");
        }

        String imageData = "data:" + mimeType
                + ";base64," + base64Image;

        Map<String, Object> imageContent = Map.of(
                "type", "image_url",
                "image_url", Map.of("url", imageData)
        );

        Map<String, Object> textContent = Map.of(
                "type", "text",
                "text", """
                    You are RoadEye AI, a road safety inspection assistant.
                    Analyze the uploaded road image.
                    Identify visible potholes, cracks, damaged surfaces,
                    debris, or other visible road hazards.
                    Explain the visual evidence.
                    Suggest severity as Low, Medium, or High.
                    If no hazard is clearly visible, say so.
                    Do not claim certainty when the image is unclear.
                    Return a clear, short report with:
                    1. Possible hazard
                    2. Visual evidence
                    3. Suggested severity
                    4. Recommended action
                    """
        );

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", List.of(textContent, imageContent)
                        )
                ),
                "max_tokens", 400,
                "temperature", 0.2
        );

        Map<?, ?> response = restClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        if (response == null) {
            throw new IllegalStateException(
                    "NVIDIA AI returned an empty response.");
        }

        List<?> choices = (List<?>) response.get("choices");

        if (choices == null || choices.isEmpty()) {
            throw new IllegalStateException(
                    "NVIDIA AI did not return an analysis.");
        }

        Map<?, ?> firstChoice = (Map<?, ?>) choices.get(0);
        Map<?, ?> message = (Map<?, ?>) firstChoice.get("message");

        return String.valueOf(message.get("content"));
    }
}