package com.ronik.ticket_management_system.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        for(FieldError fielderror: ex.getBindingResult().getFieldErrors()){
            errors.put(fielderror.getField(),fielderror.getDefaultMessage());
        }
        return errors;
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public String handleTicketNotFound(TicketNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleHttpMessageNotReadable(HttpMessageNotReadableException ex){
        return "Invalid priority: Allowed p1,p2,p3,p4";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
