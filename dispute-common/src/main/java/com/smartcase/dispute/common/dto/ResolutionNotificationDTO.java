package com.smartcase.dispute.common.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResolutionNotificationDTO {
    private String disputeId;
    private String customerId;
    private String resolutionStatus;
    private String resolutionDetails;
    private BigDecimal refundAmount;
    private LocalDateTime resolutionDate;
}
