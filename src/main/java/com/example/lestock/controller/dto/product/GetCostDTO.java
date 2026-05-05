package com.example.lestock.controller.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GetCostDTO(
        Long id,
        String name,
        Double unitPrice) {
}
