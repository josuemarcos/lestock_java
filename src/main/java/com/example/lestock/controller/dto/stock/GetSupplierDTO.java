package com.example.lestock.controller.dto.stock;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Supplier")
public record GetSupplierDTO(
        String name,
        String description,
        String contact,
        String socialMedia,
        String address
) {
}
