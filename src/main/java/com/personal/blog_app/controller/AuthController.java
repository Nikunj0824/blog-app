package com.personal.blog_app.controller;

import com.personal.blog_app.dto.JwtAuthRequest;
import com.personal.blog_app.dto.JwtAuthResponse;
import com.personal.blog_app.security.JwtHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtHelper jwtHelper, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        System.out.println("REQ: " + jwtAuthRequest.toString());
        authenticate(jwtAuthRequest.getUserName(), jwtAuthRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUserName());
        String token = jwtHelper.generateToken(userDetails);
        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.CREATED);
    }

    private void authenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
