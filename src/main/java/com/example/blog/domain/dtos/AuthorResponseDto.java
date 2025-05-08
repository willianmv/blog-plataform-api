package com.example.blog.domain.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthorResponseDto(
        UUID id,
        String name
) {
}
