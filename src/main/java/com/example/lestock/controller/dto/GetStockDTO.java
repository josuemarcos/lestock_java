package com.example.lestock.controller.dto;
import com.example.lestock.model.Material;

public record GetStockDTO(
        Long id,
        Float currentQuantity,
        Float averageCost,
        Material material
) {
}
