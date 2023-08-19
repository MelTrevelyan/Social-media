package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.exception.AppError;
import com.meltrevelyan.socialmedia.user.dto.JwtRequest;
import com.meltrevelyan.socialmedia.user.dto.JwtResponse;
import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<Object> createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Wrong username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public ResponseEntity<Object> createNewUser(UserRegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(registrationDto.getUsername())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "User with chosen name already exists"), HttpStatus.BAD_REQUEST);
        }
        UserOutDto outDto = userService.addUser(registrationDto);
        return ResponseEntity.ok(outDto);
    }
}
