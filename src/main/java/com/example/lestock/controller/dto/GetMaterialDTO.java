package com.example.lestock.controller.dto;

public record GetMaterialDTO(
        Long id,
        Float price,
        String description,
        String brand,
        Integer averageDeliveryTime,
        SupplierDTO supplier,
        MaterialTypeDTO materialType
) {
}
