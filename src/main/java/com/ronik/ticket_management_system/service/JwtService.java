package com.ronik.ticket_management_system.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
//import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey(){

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractAllClaims(String token){

        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();       
    }

    public String generateToken(UserDetails userDetails){

        Date now = new Date();
        
        return Jwts.builder().subject(userDetails.getUsername()).issuedAt(now).expiration(new Date(now.getTime() + jwtExpiration)).signWith(getSigningKey()).compact();
    }



    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
