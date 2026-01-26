package com.example.lestock.controller.dto.stock;

public record GetStockDTO(
        Long id,
        Float currentQuantity,
        Float averageCost,
        MaterialTypeDTO materialType
) {
}
