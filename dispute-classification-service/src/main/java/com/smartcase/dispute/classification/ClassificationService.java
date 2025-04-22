package com.smartcase.dispute.classification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.smartcase.dispute.model.DisputeRequest;
import com.smartcase.dispute.model.ClassifiedDispute;
import java.math.BigDecimal;

@Service
public class ClassificationService {

    private final KafkaTemplate<String, ClassifiedDispute> kafkaTemplate;

    @Autowired
    public ClassificationService(KafkaTemplate<String, ClassifiedDispute> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "dispute.submitted", groupId = "dispute-classification")
    public void classifyDispute(DisputeRequest disputeRequest) {
        // Simple classification logic - in a real app, this would be more sophisticated
        ClassifiedDispute classifiedDispute = new ClassifiedDispute();
        classifiedDispute.setDisputeRequest(disputeRequest);
        
        // Example logic: classify based on amount
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
        
        System.out.println("Classified dispute: " + disputeRequest.getTransactionReference() +
                           " as " + classifiedDispute.getCategory() +
                           " with priority " + classifiedDispute.getPriority());
    }
} 