package com.example.lestock.controller.dto.stock;

import jakarta.validation.constraints.NotBlank;

public record SaveSupplierDTO(
        @NotBlank(message = "Name can't be blank")
        String name,
        String description,
        String contact,
        String socialMedia,
        String address
) {
}
