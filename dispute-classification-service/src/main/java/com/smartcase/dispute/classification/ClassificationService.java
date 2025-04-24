package com.smartcase.dispute.classification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.smartcase.dispute.common.dto.DisputeRequest;
import com.smartcase.dispute.model.ClassifiedDispute;
import java.math.BigDecimal;
import jakarta.annotation.PostConstruct;

@Service
public class ClassificationService {

    private final KafkaTemplate<String, ClassifiedDispute> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ClassificationService.class);

    @Autowired
    public ClassificationService(KafkaTemplate<String, ClassifiedDispute> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void init() {
        logger.info("ClassificationService initialized and ready to process disputes");
    }

    @KafkaListener(topics = "dispute.intake", groupId = "dispute-classification", containerFactory = "kafkaListenerContainerFactory")
    public void classifyDispute(DisputeRequest disputeRequest) {
        // Log the incoming dispute request details
        logger.info("Received dispute request: customerId={}, transactionId={}, amount={}, reason={}",
                disputeRequest.getCustomerId(),
                disputeRequest.getTransactionId(),
                disputeRequest.getAmount(),
                disputeRequest.getReason());

        // Map common DTO to model.DisputeRequest
        com.smartcase.dispute.model.DisputeRequest modelRequest = new com.smartcase.dispute.model.DisputeRequest();
        modelRequest.setCustomerId(disputeRequest.getCustomerId());
        modelRequest.setTransactionReference(disputeRequest.getTransactionId());
        modelRequest.setAmount(disputeRequest.getAmount());
        modelRequest.setReason(disputeRequest.getReason());

        ClassifiedDispute classifiedDispute = new ClassifiedDispute();
        classifiedDispute.setDisputeRequest(modelRequest);
        
        // Classification logic
        if (disputeRequest.getAmount().compareTo(new BigDecimal(1000)) > 0) {
            classifiedDispute.setCategory("FRAUD");
            classifiedDispute.setPriority(1); // High priority
        } else if (disputeRequest.getReason().toLowerCase().contains("fraud")) {
            classifiedDispute.setCategory("FRAUD");
            classifiedDispute.setPriority(1); // High priority
        } else if (disputeRequest.getAmount().compareTo(new BigDecimal(500)) > 0) {
            classifiedDispute.setCategory("BILLING_ERROR");
            classifiedDispute.setPriority(2); // Medium priority
        } else {
            classifiedDispute.setCategory("GENERAL");
            classifiedDispute.setPriority(3); // Low priority
        }
        
        // Send the classified dispute to the next topic
        kafkaTemplate.send("dispute.classified", classifiedDispute);
        
        logger.info("Classified dispute: transactionId={} as {} with priority {}",
                disputeRequest.getTransactionId(),
                classifiedDispute.getCategory(),
                classifiedDispute.getPriority());
    }
} 