package com.api_contact_manager.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private int code;
    private LocalDateTime timestamp;
}
