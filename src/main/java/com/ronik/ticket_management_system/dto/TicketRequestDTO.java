package com.ronik.ticket_management_system.dto;

import com.ronik.ticket_management_system.enums.Priority;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {
    @Schema(
        description = "Name of the person raising the ticket",
        example = "Ronik"
    )
    @NotBlank(message = "Requester name is required")
    private String requesterName;

    @Schema(
        description = "Short summary of the issue",
        example = "Laptop is not connecting to VPN"
    )
    @NotBlank(message = "Subject is required")
    private String subject;

    @Schema(
        description = "Detailed description of the issue",
        example = "Unable to connect to the corporate vpn after restarting the laptop"
    )
    private String description;

    @Schema(
        description = "Priority of the ticket",
        example = "P2"
    )
    private Priority priority;

}
