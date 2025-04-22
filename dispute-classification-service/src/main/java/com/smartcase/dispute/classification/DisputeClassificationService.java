package com.smartcase.dispute.classification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import com.smartcase.dispute.model.DisputeRequest;

@Service
public class DisputeClassificationService {

    private final KafkaTemplate<String, ClassifiedDispute> kafkaTemplate;
    
    public DisputeClassificationService(KafkaTemplate<String, ClassifiedDispute> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "dispute.intake", groupId = "dispute-classification")
    public void classifyDispute(DisputeRequest dispute) {
        // Mock classification logic
        String category = "FRAUD"; // In real app, this would call ML service
        int priority = dispute.getAmount().compareTo(new BigDecimal("1000")) > 0 ? 1 : 2;
        
        ClassifiedDispute classifiedDispute = new ClassifiedDispute();
        classifiedDispute.setDisputeRequest(dispute);
        classifiedDispute.setCategory(category);
        classifiedDispute.setPriority(priority);
        
        // Send to next topic
        kafkaTemplate.send("dispute.classified", classifiedDispute);
    }
} 