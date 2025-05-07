package com.example.blog.domain.dtos;

import lombok.Builder;

@Builder
public record AuthResponseDto (
        String token,
        long expiresIn
){
}
