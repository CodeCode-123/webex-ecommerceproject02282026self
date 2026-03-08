package com.code.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.LoginDTO;
import com.code.api.dto.RegisterDTO;
import com.code.api.entity.Users;
import com.code.api.response.LoginResponse;
import com.code.api.service.AuthenticationService;
import com.code.api.service.JwtService;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
    	this.jwtService = jwtService;
    	this.authenticationService = authenticationService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Users> register(@RequestBody RegisterDTO registerDTO) {
    	Users registeredUser = authenticationService.signup(registerDTO);
    	return ResponseEntity.ok(registeredUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDTO loginUserDTO) {
    	Users authenticatedUser = authenticationService.authenticate(loginUserDTO);
    	String jwtToken = jwtService.generateToken(authenticatedUser);
    	LoginResponse loginResponse = new LoginResponse();
    	loginResponse.setToken(jwtToken);
    	loginResponse.setExpiresIn(jwtService.getExpirationTime());
    	return ResponseEntity.ok(loginResponse);
    }
    

}
