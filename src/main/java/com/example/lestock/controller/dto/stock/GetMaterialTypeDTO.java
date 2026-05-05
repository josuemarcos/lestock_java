package com.example.lestock.controller.dto.stock;

import jakarta.validation.constraints.NotBlank;


public record GetMaterialTypeDTO(
        Long id,
        String name,
        String metricUnit,
        String brand
) {
}
