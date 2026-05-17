package com.ner.analytics;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*")
public class StatsController {

    private final EntityRecordRepository repository;

    public StatsController(EntityRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> getStats() {
        List<EntityRecord> all = repository.findAll();

        Map<String, Long> byLabel = all.stream()
            .collect(Collectors.groupingBy(EntityRecord::getEntityLabel, Collectors.counting()));

        Map<String, Long> topEntities = all.stream()
            .collect(Collectors.groupingBy(EntityRecord::getEntityText, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("totalEntities", all.size());
        response.put("byLabel", byLabel);
        response.put("topEntities", topEntities);

        return ResponseEntity.ok(response);
    }
}
