package com.meltrevelyan.socialmedia.user.controller;

import com.meltrevelyan.socialmedia.user.dto.JwtRequest;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<Object> createAuthToken(@Valid @RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        return authService.createNewUser(registrationDto);
    }
}
