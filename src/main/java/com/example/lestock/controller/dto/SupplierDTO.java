package com.example.lestock.controller.dto;

import com.example.lestock.validator.ValidSupplier;
import jakarta.validation.constraints.NotBlank;

@ValidSupplier
public record SupplierDTO(
        Long id,
        @NotBlank(message = "Name can't be blank")
        String name,
        String description,
        String contact,
        String socialMedia,
        String address
) {
}
