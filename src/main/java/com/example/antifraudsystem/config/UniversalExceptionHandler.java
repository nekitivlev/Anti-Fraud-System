package com.example.antifraudsystem.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * A universal handler to catch and manage exceptions throughout the application.
 */
@ControllerAdvice
public class UniversalExceptionHandler {

    /**
     * Manages ConstraintViolationException, responding with a BAD_REQUEST status.
     *
     * @param exception the caught ConstraintViolationException
     * @return a ResponseEntity with a BAD_REQUEST status
     */
    @ExceptionHandler
    ResponseEntity<Exception> handleConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}