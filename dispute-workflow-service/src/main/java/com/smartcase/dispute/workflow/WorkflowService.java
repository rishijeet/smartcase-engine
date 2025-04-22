package com.smartcase.dispute.workflow;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import java.time.LocalDateTime;
import com.smartcase.dispute.model.ClassifiedDispute;
import com.smartcase.dispute.model.ManualTask;
import com.smartcase.dispute.model.TaskRepository;

@Service
public class WorkflowService {

    private final TaskRepository taskRepository;

    @Autowired
    public WorkflowService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @KafkaListener(topics = "dispute.classified", groupId = "dispute-workflow")
    public void processClassifiedDispute(ClassifiedDispute classifiedDispute) {
        // For high priority or fraud disputes, create a manual review task
        if (classifiedDispute.getPriority() == 1 || "FRAUD".equals(classifiedDispute.getCategory())) {
            createManualTask(classifiedDispute);
        } else {
            // For standard cases, implement automated handling
            // In a real system, this would integrate with payment systems, etc.
            System.out.println("Auto-processing dispute: " + classifiedDispute.getDisputeRequest().getTransactionReference());
        }
    }

    private void createManualTask(ClassifiedDispute classifiedDispute) {
        ManualTask task = new ManualTask();
        task.setId(UUID.randomUUID().toString());
        task.setDisputeReference(classifiedDispute.getDisputeRequest().getTransactionReference());
        task.setCustomerId(classifiedDispute.getDisputeRequest().getCustomerId());
        task.setAmount(classifiedDispute.getDisputeRequest().getAmount());
        task.setPriority(classifiedDispute.getPriority());
        task.setCategory(classifiedDispute.getCategory());
        task.setStatus("PENDING");
        task.setCreatedAt(LocalDateTime.now());
        
        taskRepository.save(task);
        
        System.out.println("Created manual task: " + task.getId() + " for dispute: " + 
                           classifiedDispute.getDisputeRequest().getTransactionReference());
    }
} 