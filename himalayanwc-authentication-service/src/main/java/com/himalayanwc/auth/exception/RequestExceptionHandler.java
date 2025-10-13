package com.himalayanwc.auth.exception;

import com.himalayanwc.auth.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Error>handleResourceNotFoundException(ResourceNotFoundException e){
        Error error=new Error(e.getMessage(),e.getStatus());
        return new ResponseEntity<>(error,e.getStatus());
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Error>handleResourceAlreadyExistsException(ResourceAlreadyExistsException e){
        Error error=new Error(e.getMessage(),e.getStatus());
        return new ResponseEntity<>(error,e.getStatus());
    }
}
