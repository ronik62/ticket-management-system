package com.ronik.ticket_management_system.entity;

import java.time.LocalDateTime;

import com.ronik.ticket_management_system.enums.Priority;
import com.ronik.ticket_management_system.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // @NotBlank(message= "Requestor name is required")  validation not required here as we are validating it in dtos
    private String requesterName;
    private String teamAssigned;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    // @NotBlank(message = "Subject is required")  same here
    private String subject;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;
    
}