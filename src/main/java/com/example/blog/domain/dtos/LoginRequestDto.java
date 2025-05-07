package com.example.blog.domain.dtos;

import lombok.Builder;

@Builder
public record LoginRequestDto(
        String email,
        String password
) {
}
