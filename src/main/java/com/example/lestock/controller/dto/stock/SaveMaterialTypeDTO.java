package com.example.lestock.controller.dto.stock;

import jakarta.validation.constraints.NotBlank;

public record SaveMaterialTypeDTO(
        @NotBlank(message = "Can't be blank")
        String name,
        @NotBlank(message = "Can't be blank")
        String metricUnit,
        String brand
) {
}
