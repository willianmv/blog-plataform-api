package com.example.blog.domain.dtos;

import com.example.blog.domain.entities.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDto(
        String name,
        String email,
        Set<Role> roles,
        LocalDateTime createdAt
) {
}
