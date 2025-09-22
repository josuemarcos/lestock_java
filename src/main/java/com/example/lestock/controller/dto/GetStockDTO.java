package com.example.lestock.controller.dto;

public record GetStockDTO(
        Long id,
        Float currentQuantity,
        Float averageCost,
        GetMaterialDTO material
) {
}
