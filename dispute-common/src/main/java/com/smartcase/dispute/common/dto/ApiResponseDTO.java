package com.smartcase.dispute.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private String errorCode;
    private T data;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
