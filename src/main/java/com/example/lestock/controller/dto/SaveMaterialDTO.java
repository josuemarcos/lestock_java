package com.example.lestock.controller.dto;

import jakarta.validation.constraints.NotNull;

public record SaveMaterialDTO(
        Float minimumPurchaseAmount,
        Float price,
        String description,
        String brand,
        Integer averageDeliveryTime,
        @NotNull(message = "Can't be blank!")
        Long IdSupplier,
        @NotNull(message = "Can't be blank!")
        Long IdMaterialType
) {
}
