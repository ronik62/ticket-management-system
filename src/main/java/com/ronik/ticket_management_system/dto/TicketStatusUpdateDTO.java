package com.ronik.ticket_management_system.dto;

import com.ronik.ticket_management_system.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatusUpdateDTO {
    
    private Status status;
}
