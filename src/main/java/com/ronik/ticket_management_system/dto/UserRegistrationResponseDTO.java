package com.ronik.ticket_management_system.dto;

import com.ronik.ticket_management_system.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponseDTO {
    
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String message;
}
