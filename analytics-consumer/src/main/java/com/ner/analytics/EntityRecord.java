package com.ner.analytics;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entity_records")
public class EntityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageId;
    private String originalText;
    private String entityText;
    private String entityLabel;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void setMessageId(String messageId) { this.messageId = messageId; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }
    public void setEntityText(String entityText) { this.entityText = entityText; }
    public String getEntityLabel() { return entityLabel; }
    public String getEntityText() { return entityText; }
    public void setEntityLabel(String entityLabel) { this.entityLabel = entityLabel; }
}
