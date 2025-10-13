package com.himalayanwc.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class Error {
    private String massage;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public Error(String massage, HttpStatus status) {
        this.massage = massage;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
