package com.smartcase.dispute.model;

import java.io.Serializable;

public class ClassifiedDispute implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private DisputeRequest disputeRequest;
    private String category;
    private int priority;
    
    // Default constructor for deserialization
    public ClassifiedDispute() {
    }

    // Getters and Setters
    public DisputeRequest getDisputeRequest() {
        return disputeRequest;
    }

    public void setDisputeRequest(DisputeRequest disputeRequest) {
        this.disputeRequest = disputeRequest;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
} 