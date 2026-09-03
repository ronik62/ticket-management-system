package com.ronik.ticket_management_system.security;


import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.ronik.ticket_management_system.entity.AppUser;
import com.ronik.ticket_management_system.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        
        Optional<AppUser> optionalUser = appUserRepository.findByUsername(username);

        if(optionalUser.isPresent()){
            AppUser existingUser = optionalUser.get();
            return User.builder().username(existingUser.getUsername()).password(existingUser.getPassword()).roles(existingUser.getRole().name()).build();
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username );
        }
    }
}
