package com.himalayanwc.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends RuntimeException{
    private final String message;
    @Getter
    private final HttpStatus status;

    public ResourceAlreadyExistsException(String message) {
        this.message = message;
        this.status = HttpStatus.CONFLICT;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
