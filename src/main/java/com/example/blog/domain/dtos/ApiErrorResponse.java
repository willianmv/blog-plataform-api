package com.example.blog.domain.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record ApiErrorResponse(
        int status,
        String message,
        List<FieldError> errors) {

    @Builder
    public static class FieldError{
        private String field;
        private String error;
    }

}
