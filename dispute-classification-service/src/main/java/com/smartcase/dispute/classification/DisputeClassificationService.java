package com.smartcase.dispute.classification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import com.smartcase.dispute.common.dto.DisputeRequest;
import com.smartcase.dispute.model.ClassifiedDispute;

@Service
public class DisputeClassificationService {

    private static final Logger logger = LoggerFactory.getLogger(DisputeClassificationService.class);
    private final KafkaTemplate<String, ClassifiedDispute> kafkaTemplate;
    
    public DisputeClassificationService(KafkaTemplate<String, ClassifiedDispute> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
        topics = "dispute.intake", 
        groupId = "dispute-classification",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void classifyDispute(DisputeRequest dispute) {
        logger.info("Received dispute request: customerId={}, transactionId={}, amount={}, reason={} ",
            dispute.getCustomerId(), dispute.getTransactionId(), dispute.getAmount(), dispute.getReason());
        // Mock classification logic
        String category = "FRAUD"; // In real app, this would call ML service
        logger.info("Assigned category {} based on mock logic", category);
        int priority = dispute.getAmount().compareTo(new BigDecimal("1000")) > 0 ? 1 : 2;
        logger.info("Determined priority {} based on amount {}", priority, dispute.getAmount());
        
        // Convert DTO to model object
        com.smartcase.dispute.model.DisputeRequest modelRequest = new com.smartcase.dispute.model.DisputeRequest();
        modelRequest.setCustomerId(dispute.getCustomerId());
        modelRequest.setTransactionReference(dispute.getTransactionId());
        modelRequest.setAmount(dispute.getAmount());
        modelRequest.setReason(dispute.getReason());
        
        ClassifiedDispute classifiedDispute = new ClassifiedDispute();
        classifiedDispute.setDisputeRequest(modelRequest);
        classifiedDispute.setCategory(category);
        classifiedDispute.setPriority(priority);
        logger.debug("Prepared ClassifiedDispute object: {}", classifiedDispute);
        
        // Send to next topic
        kafkaTemplate.send("dispute.classified", classifiedDispute);
        logger.info("Published ClassifiedDispute for customer {} to Kafka topic dispute.classified", dispute.getCustomerId());
        
        System.out.println("Classified dispute for customer: " + dispute.getCustomerId() +
                       " as " + category +
                       " with priority " + priority);
    }
} 