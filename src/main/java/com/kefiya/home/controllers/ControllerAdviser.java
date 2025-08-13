package com.kefiya.home.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RestController
@Slf4j
public class ControllerAdviser {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException( IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException( EntityNotFoundException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
    }
    @ExceptionHandler( Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException( Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
    }

}
