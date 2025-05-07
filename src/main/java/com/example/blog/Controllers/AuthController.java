package com.example.blog.Controllers;

import com.example.blog.domain.dtos.AuthResponseDto;
import com.example.blog.domain.dtos.LoginRequestDto;
import com.example.blog.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        UserDetails userDetails = authenticationService.authenticate(loginRequestDto.email(), loginRequestDto.password());
        String tokenValue = authenticationService.generateToken(userDetails);
        AuthResponseDto authResponse = AuthResponseDto.builder()
                .token(tokenValue)
                .expiresIn(86400)
                .build();
        return ResponseEntity.ok(authResponse);
    }

}
