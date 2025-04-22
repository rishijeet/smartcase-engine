package com.smartcase.dispute.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DisputeSubmissionDTO {
    private String transactionId;
    private String customerId;
    private BigDecimal amount;
    private String reasonCode;
    private LocalDateTime submissionDate;
    private String customerNotes;
}
