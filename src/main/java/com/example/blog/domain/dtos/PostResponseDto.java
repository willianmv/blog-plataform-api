package com.example.blog.domain.dtos;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record PostResponseDto (
        UUID id,
        String title,
        String content,
        AuthorResponseDto author,
        CategoryDto category,
        Set<TagResponseDto> tags,
        int readingTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
