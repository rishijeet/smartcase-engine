package com.smartcase.dispute.common.dto;

public class DisputeResponse {
    private String status;  // "SUCCESS", "ERROR"
    private String message;
    private String disputeId;
    
    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getDisputeId() { return disputeId; }
    public void setDisputeId(String disputeId) { this.disputeId = disputeId; }
}
