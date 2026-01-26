package com.example.lestock.controller.dto.stock;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Supplier")
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
