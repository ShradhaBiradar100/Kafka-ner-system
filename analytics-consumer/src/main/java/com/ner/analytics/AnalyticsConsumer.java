package com.ner.analytics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsConsumer {

    private final EntityRecordRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AnalyticsConsumer(EntityRecordRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "enriched-entities", groupId = "analytics-group")
    public void consume(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            String messageId = root.get("message_id").asText();
            String originalText = root.get("original_text").asText();
            JsonNode entities = root.get("entities");

            for (JsonNode entity : entities) {
                EntityRecord record = new EntityRecord();
                record.setMessageId(messageId);
                record.setOriginalText(originalText);
                record.setEntityText(entity.get("text").asText());
                record.setEntityLabel(entity.get("label").asText());
                repository.save(record);
            }
            System.out.println("Saved " + entities.size() + " entities to MySQL");
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
