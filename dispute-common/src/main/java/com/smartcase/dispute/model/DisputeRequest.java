package com.smartcase.dispute.model;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DisputeRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String customerId;
    private String transactionReference;
    private BigDecimal amount;
    private String transactionId;
    private String reason;
    
    // Default constructor for serialization
    public DisputeRequest() {
    }
} 