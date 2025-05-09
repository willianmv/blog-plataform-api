package com.example.blog.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters")
        String name,

        @NotBlank(message = "Name is required")
        @Email(message = "Email format invalid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8,message = "Password must have at least {min} characters")
        String password
) {
}
