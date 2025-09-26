package com.example.lestock.controller.dto.errors;

public record FieldErrorDTO(
        String field,
        String message,
        String code
) {
}
