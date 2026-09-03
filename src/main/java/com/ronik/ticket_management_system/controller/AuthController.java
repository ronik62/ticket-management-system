package com.ronik.ticket_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ronik.ticket_management_system.dto.LoginRequestDTO;
import com.ronik.ticket_management_system.dto.LoginResponseDTO;
import com.ronik.ticket_management_system.dto.UserRegistrationDTO;
import com.ronik.ticket_management_system.dto.UserRegistrationResponseDTO;
import com.ronik.ticket_management_system.service.AuthService;


import jakarta.validation.Valid;

@RestController
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserRegistrationResponseDTO> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        
        
        UserRegistrationResponseDTO user = authService.register(userRegistrationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){

        LoginResponseDTO token = authService.login(loginRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
