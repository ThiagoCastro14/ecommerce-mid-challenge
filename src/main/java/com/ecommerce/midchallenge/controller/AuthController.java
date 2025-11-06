package com.ecommerce.midchallenge.controller;

import com.ecommerce.midchallenge.dto.user.*;
import com.ecommerce.midchallenge.security.jwt.JwtService;
import com.ecommerce.midchallenge.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());        
        
        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .build();
    }
    
}