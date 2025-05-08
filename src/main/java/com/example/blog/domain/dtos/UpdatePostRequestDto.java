package com.example.blog.domain.dtos;

import com.example.blog.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UpdatePostRequestDto(
        @NotNull(message = "ID is required")
        UUID id,

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between {min} and {max} characters")
        String title,

        @NotBlank(message = "Content is required")
        @Size(min = 10, max = 50000, message = "Content must be between {min} and {max} characters")
        String content,

        @NotNull(message = "Category ID is required")
        UUID categoryId,

        @Size(max = 10, message = "Maximum {max} tags allowed")
        Set<UUID> tagIds,

        @NotNull(message = "Status is required")
        PostStatus status
) {
}
