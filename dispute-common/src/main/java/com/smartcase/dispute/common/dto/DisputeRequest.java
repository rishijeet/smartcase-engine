package com.smartcase.dispute.common.dto;

import java.math.BigDecimal;

public class DisputeRequest {
    private String transactionId;
    private String customerId;
    private BigDecimal amount;
    private String reason;  // e.g., "FRAUD", "BILLING_ERROR"
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
