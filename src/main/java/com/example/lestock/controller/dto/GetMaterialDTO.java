package com.example.lestock.controller.dto;

public record GetMaterialDTO(
        Long id,
        Float price,
        Float minimumPurchaseAmount,
        Float pricePerAmount,
        String description,
        Integer averageDeliveryTime,
        SupplierDTO supplier,
        MaterialTypeDTO materialType
) {
}
