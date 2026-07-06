package com.ronik.ticket_management_system.dto;

import java.time.LocalDateTime;

import com.ronik.ticket_management_system.enums.Priority;
import com.ronik.ticket_management_system.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {

    @Schema(
        description = "Unique ticket identifier",
        example = "101"
    )
    private Long id;

    @Schema(
        description = "Name of the person who raised ticket",
        example = "Ronik"
    )
    private String requesterName;

    @Schema(
        description = "Short summary of the issue",
        example = "VPN connecting issue"
    )
    private String subject;

    @Schema(
        description = "Priority of the ticket",
        example = "P2"
    )
    private Priority priority;

    @Schema(
        description = "Current status of the ticket",
        example = "OPEN"
    )
    private Status status;

    @Schema(
        description = "Date and time when ticket was created",
        example = "2026-07-06T10:30:00"
    )
    private LocalDateTime createdAt;
}
