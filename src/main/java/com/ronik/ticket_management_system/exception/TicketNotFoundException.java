package com.ronik.ticket_management_system.exception;

public class TicketNotFoundException extends RuntimeException{
    
    public TicketNotFoundException(String message){
        super(message);
    }
}
