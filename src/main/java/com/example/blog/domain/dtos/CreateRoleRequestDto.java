package com.example.blog.domain.dtos;

import com.example.blog.domain.RoleName;
import jakarta.validation.constraints.NotNull;

public record CreateRoleRequestDto(
        @NotNull(message = "Role name is required")
        RoleName name
) {
}
