
package com.ner.producer;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TextProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "raw-text";

    public TextProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String text) {
        String messageId = UUID.randomUUID().toString();
        kafkaTemplate.send(TOPIC, messageId, text);
        System.out.println("Published message ID: " + messageId);
    }
}