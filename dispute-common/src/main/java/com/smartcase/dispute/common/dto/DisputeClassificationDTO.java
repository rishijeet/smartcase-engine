package com.smartcase.dispute.common.dto;

import lombok.Data;

@Data
public class DisputeClassificationDTO {
    private String disputeId;
    private String category;
    private String priority;
    private Integer riskScore;
    private boolean requiresManualReview;
}
