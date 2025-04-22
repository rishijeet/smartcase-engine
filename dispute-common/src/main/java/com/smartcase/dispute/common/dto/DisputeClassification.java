package com.smartcase.dispute.common.dto;

public class DisputeClassification {
    private String transactionId;
    private String category;  // "FRAUD", "BILLING_ERROR", "CUSTOMER_SERVICE"
    private String priority;  // "HIGH", "MEDIUM", "LOW"
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}
