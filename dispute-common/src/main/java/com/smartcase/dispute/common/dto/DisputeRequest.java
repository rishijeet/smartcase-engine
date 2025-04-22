package com.smartcase.dispute.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DisputeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String transactionId;
    private String customerId;
    private BigDecimal amount;
    private String reason;  // e.g., "FRAUD", "BILLING_ERROR"
    
    // Default constructor for deserialization
    public DisputeRequest() {
    }
    
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
