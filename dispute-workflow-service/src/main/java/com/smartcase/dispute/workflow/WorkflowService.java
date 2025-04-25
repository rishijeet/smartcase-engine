package com.smartcase.dispute.workflow;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import java.time.LocalDateTime;
import com.smartcase.dispute.model.ClassifiedDispute;
import com.smartcase.dispute.model.ManualTask;
import com.smartcase.dispute.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WorkflowService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    @Autowired
    public WorkflowService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @KafkaListener(topics = "dispute.classified", groupId = "dispute-workflow")
    public void processClassifiedDispute(ClassifiedDispute classifiedDispute) {
        createTask(classifiedDispute);
        
        logger.info("Processed dispute {} (Category: {}, Priority: {})", 
            classifiedDispute.getDisputeRequest().getTransactionReference(),
            classifiedDispute.getCategory(),
            classifiedDispute.getPriority());
    }

    private void createTask(ClassifiedDispute classifiedDispute) {
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
    }
} 