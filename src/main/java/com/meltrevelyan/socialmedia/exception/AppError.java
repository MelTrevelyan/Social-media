package com.meltrevelyan.socialmedia.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppError {

    private int status;
    private String message;
    private String description;
    private LocalDateTime timestamp;

    public AppError(int status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }
}
