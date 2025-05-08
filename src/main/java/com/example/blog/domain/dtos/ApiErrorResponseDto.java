package com.example.blog.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record ApiErrorResponseDto(
        int status,
        String message,
        List<FieldError> errors) {

    @Getter
    @Builder
    public static class FieldError{
        private String field;
        private String error;
    }

}
