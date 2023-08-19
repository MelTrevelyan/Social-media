package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.user.dto.JwtRequest;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<Object> createAuthToken(JwtRequest authRequest);

    ResponseEntity<Object> createNewUser(UserRegistrationDto registrationDto);
}
