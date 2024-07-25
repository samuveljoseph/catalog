package com.ecommerce.catalog.business.util;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private String status;
    private T data;
    private String message;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(String status, T data,String message) {
        this();
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
