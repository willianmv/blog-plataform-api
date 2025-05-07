package com.example.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCategoryRequestDto(
        @NotBlank(message = "Category name is required")
        @Size(min =2, max = 50, message = "Category name must be between {min} and {^max} characters")
        @Pattern(regexp = "^[\\p{L}0-9\\s-]+$", message = "Category name can only contain letters, numbers, spaces, and hyphens")
        String name
) {
}
