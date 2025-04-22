package com.smartcase.dispute.common.dto;

public class DisputeTask {
    private String taskId;
    private String assignee;
    private String status;  // "PENDING", "APPROVED", "REJECTED"
    private String comments;
    
    // Getters and Setters
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
    
    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
