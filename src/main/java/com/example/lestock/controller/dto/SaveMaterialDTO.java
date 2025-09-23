package com.example.lestock.controller.dto;

import com.example.lestock.validator.ValidMaterial;
import jakarta.validation.constraints.NotNull;

@ValidMaterial
public record SaveMaterialDTO(
        Float minimumPurchaseAmount,
        Float price,
        Float pricePerAmount,
        String description,
        Integer averageDeliveryTime,
        @NotNull(message = "Can't be blank!")
        Long IdSupplier,
        @NotNull(message = "Can't be blank!")
        Long IdMaterialType
) {
}
