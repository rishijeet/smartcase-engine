package com.smartcase.dispute.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DisputeRequest {
    private String customerId;
    private String transactionReference;
    private BigDecimal amount;
    private String reason;
} 