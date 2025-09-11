package com.example.lestock.controller.dto;

public record GetMaterialDTO(
        Long id,
        Float price,
        String description,
        String brand,
        Integer averageDeliveryTime,
        Long supplier_id,
        Long material_type_id,
        SupplierDTO supplier,
        MaterialTypeDTO materialType
) {
}
