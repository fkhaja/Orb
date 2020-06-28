package com.sin.orb.controller;

import com.sin.orb.exception.ConflictingRequestException;
import com.sin.orb.payload.ApiResponse;
import com.sin.orb.payload.AuthResponse;
import com.sin.orb.payload.LoginRequest;
import com.sin.orb.payload.SignUpRequest;
import com.sin.orb.security.TokenProvider;
import com.sin.orb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    @ApiOperation("Authenticate user")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    @ApiOperation("Register a new user")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new ConflictingRequestException("Email address already in use");
        }
        userService.registerUser(request);
        ApiResponse response = new ApiResponse(true, "User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}