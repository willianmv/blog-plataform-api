package com.example.blog.controllers;

import com.example.blog.domain.dtos.AuthResponseDto;
import com.example.blog.domain.dtos.LoginRequestDto;
import com.example.blog.domain.dtos.RegisterRequestDto;
import com.example.blog.domain.dtos.UserResponseDto;
import com.example.blog.domain.entities.User;
import com.example.blog.mappers.UserMapper;
import com.example.blog.services.AuthenticationService;
import com.example.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        User userToSave = userMapper.toUser(registerRequestDto);
        User savedUser = userService.createUser(userToSave);
        return ResponseEntity.ok(userMapper.toDto(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        UserDetails userDetails = authenticationService.authenticate(loginRequestDto.email(), loginRequestDto.password());
        String tokenValue = authenticationService.generateToken(userDetails);
        AuthResponseDto authResponse = AuthResponseDto.builder()
                .token(tokenValue)
                .expiresIn(86400)
                .build();
        return ResponseEntity.ok(authResponse);
    }

}
