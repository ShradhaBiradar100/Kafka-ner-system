package com.ner.producer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/text")
public class TextController {

    private final TextProducerService producerService;

    public TextController(TextProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody Map<String, String> body) {
        String text = body.get("text");
        producerService.publish(text);
        return ResponseEntity.ok(Map.of("status", "published", "text", text));
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "running"));
    }
}