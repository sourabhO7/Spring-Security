package com.glo.app.exception;

import org.springframework.http.HttpStatus;

public class EmployeeApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public EmployeeApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
