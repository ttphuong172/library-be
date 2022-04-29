package com.example.librarybe.controller;


import com.example.librarybe.config.JwtUtil;
import com.example.librarybe.config.MyUserDetailsService;
import com.example.librarybe.model.ERole;
import com.example.librarybe.model.JwtRequest;
import com.example.librarybe.model.JwtResponse;
import com.example.librarybe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @PostMapping("/signin")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        String token = null;
        String username = null;
        ERole role = null;
        String message=null;
        UserDetails userDetails;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (DisabledException  e) {
            return new JwtResponse(token, username,role,"Account disabled !");
        } catch (BadCredentialsException  e) {
            return new JwtResponse(token, username,role,"Username or password is incorrect !");
        }

        userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        token = jwtUtil.generateToken(userDetails);
        username = jwtRequest.getUsername();
        role = accountService.findById(jwtRequest.getUsername()).getRole();
        return new JwtResponse(token, username, role,"Login succesfull");
    }
}
