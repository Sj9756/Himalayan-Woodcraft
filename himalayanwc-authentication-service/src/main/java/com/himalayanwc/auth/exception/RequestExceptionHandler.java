package com.himalayanwc.auth.exception;

import com.himalayanwc.auth.model.Error;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException e) {
        Error error = new Error(e.getMessage(), e.getStatus());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Error> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        Error error = new Error(e.getMessage(), e.getStatus());
        return new ResponseEntity<>(error, e.getStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Error> handleUsernameNotFoundException(UsernameNotFoundException e) {
        Error error = new Error(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Error> handleExpiredJwtException(ExpiredJwtException e) {
        Error error = new Error(e.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error, error.getStatus());
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Error> handleJwtException(JwtException e) {
        Error error = new Error(e.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(error, error.getStatus());
    }

}
