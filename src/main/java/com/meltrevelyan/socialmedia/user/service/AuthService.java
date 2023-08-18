package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<Object> createAuthToken();

    public ResponseEntity<?> createNewUser(UserRegistrationDto registrationDto);
}
