package com.smartcase.dispute.common.dto;

public class Notification {
    private String customerId;
    private String message;  // e.g., "Your dispute is resolved"
    private String status;   // "RESOLVED", "REJECTED", "IN_PROGRESS"
    
    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
