package com.example.lestock.controller.dto.stock;

public record GetMaterialDTO(
        Long id,
        Float price,
        Float minimumPurchaseAmount,
        Float pricePerAmount,
        String description,
        Integer averageDeliveryTime,
        GetSupplierDTO supplier,
        MaterialTypeDTO materialType
) {
}
