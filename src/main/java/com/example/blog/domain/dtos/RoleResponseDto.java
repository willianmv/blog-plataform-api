package com.example.blog.domain.dtos;

import com.example.blog.domain.RoleName;

import java.time.LocalDateTime;
import java.util.UUID;

public record RoleResponseDto(
        UUID id,
        RoleName name,
        LocalDateTime createdAt
) {
}
