package com.smartcase.dispute.intake;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import com.smartcase.dispute.common.dto.DisputeRequest;
import com.smartcase.dispute.common.dto.DisputeResponse;

@RestController
@RequestMapping("/api/disputes")
public class DisputeIntakeController {

    private final KafkaTemplate<String, DisputeRequest> kafkaTemplate;

    public DisputeIntakeController(KafkaTemplate<String, DisputeRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> submitDispute(@RequestBody DisputeRequest disputeRequest) {
        // Validate request
        if (disputeRequest.getCustomerId() == null || disputeRequest.getAmount() == null) {
            return ResponseEntity.badRequest().body("Customer ID and amount are required");
        }

        // Publish to Kafka
        kafkaTemplate.send("dispute.intake", disputeRequest);
        
        return ResponseEntity.ok("Dispute received and being processed");
    }
} 