package com.incident.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNameNotNullExceptionController {

    @ExceptionHandler(UserNameIsNotNull.class)
    public ResponseEntity<Object> handleUserNameIsNotNullException(UserNameIsNotNull exception) {

        HandleException handleException = new HandleException(
            exception.getMessage(), 
            exception.getCause(), 
            HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(handleException, HttpStatus.BAD_REQUEST);
    }

}
