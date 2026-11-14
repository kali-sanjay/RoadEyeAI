package com.example.roadeeye;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AIController {

    private final NvidiaAIService nvidiaAIService;

    public AIController(NvidiaAIService nvidiaAIService) {
        this.nvidiaAIService = nvidiaAIService;
    }

    @PostMapping(
        value = "/api/analyze",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, String>> analyzeImage(
            @RequestParam("image") MultipartFile image) {

        try {
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", "Please upload a road image.")
                );
            }

            String result = nvidiaAIService.analyzeRoadImage(image);

            return ResponseEntity.ok(
                Map.of("result", result)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                Map.of(
                    "error",
                    "AI analysis failed. Check the NVIDIA API configuration and try again."
                )
            );
        }
    }
}