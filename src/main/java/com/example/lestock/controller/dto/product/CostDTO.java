package com.example.lestock.controller.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CostDTO(
        Long id,
        @NotBlank(message = "name can't be blank")
        String name,
        @NotNull(message = "unitPrice can't be null")
        Double unitPrice) {
}
