package com.ronik.ticket_management_system.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ronik.ticket_management_system.dto.LoginRequestDTO;
import com.ronik.ticket_management_system.dto.LoginResponseDTO;
import com.ronik.ticket_management_system.dto.UserRegistrationDTO;
import com.ronik.ticket_management_system.dto.UserRegistrationResponseDTO;
import com.ronik.ticket_management_system.entity.AppUser;
import com.ronik.ticket_management_system.enums.Role;
import com.ronik.ticket_management_system.exception.UserAlreadyExistsException;
import com.ronik.ticket_management_system.repository.AppUserRepository;

@Service
public class AuthService {
    
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtService jwtService){
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    private UserRegistrationResponseDTO mapToResponseDTO(AppUser appUser){

        UserRegistrationResponseDTO responseDTO = new UserRegistrationResponseDTO();

        responseDTO.setUsername(appUser.getUsername());
        responseDTO.setEmail(appUser.getEmail());
        responseDTO.setId(appUser.getId());
        responseDTO.setRole(appUser.getRole());
        responseDTO.setMessage("Registration sucessful.");

        return responseDTO;
    }

    public UserRegistrationResponseDTO register(UserRegistrationDTO userRegistrationDTO){

        Optional<AppUser> existingUser = appUserRepository.findByUsername(userRegistrationDTO.getUsername());
        Optional<AppUser> existingEmail = appUserRepository.findByEmail(userRegistrationDTO.getEmail());

        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("Username already exists.");
        }
        if(existingEmail.isPresent()){
            throw new UserAlreadyExistsException("Email already exists.");
        }
        AppUser appUser = new AppUser();
        

        appUser.setUsername(userRegistrationDTO.getUsername());
        appUser.setEmail(userRegistrationDTO.getEmail());
        appUser.setRole(Role.USER);
        appUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        AppUser registeredUser = appUserRepository.save(appUser);

        return mapToResponseDTO(registeredUser);

    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String generatedToken = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(generatedToken);
    }

    
}
