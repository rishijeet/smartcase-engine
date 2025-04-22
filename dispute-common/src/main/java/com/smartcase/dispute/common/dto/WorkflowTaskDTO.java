package com.smartcase.dispute.common.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkflowTaskDTO {
    private String taskId;
    private String disputeId;
    private String assignee;
    private String status;
    private String taskType;
    private LocalDateTime dueDate;
}
