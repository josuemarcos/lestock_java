package com.example.lestock.controller.dto;

import java.util.List;

public record ResponseErrorDTO(
        Integer status,
        String message,
        List<FieldErrorDTO> errors
) {
}
