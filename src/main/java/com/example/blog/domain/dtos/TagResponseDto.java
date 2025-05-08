package com.example.blog.domain.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TagResponseDto(
        UUID id,
        String name,
        int postCount
) {
}
