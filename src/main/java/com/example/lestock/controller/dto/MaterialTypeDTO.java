package com.example.lestock.controller.dto;

import com.example.lestock.validator.ValidMaterialType;
import jakarta.validation.constraints.NotBlank;

@ValidMaterialType
public record MaterialTypeDTO(
        Long id,
        @NotBlank(message = "Can't be blank")
        String name,
        @NotBlank(message = "Can't be blank")
        String metricUnit
) {
}
