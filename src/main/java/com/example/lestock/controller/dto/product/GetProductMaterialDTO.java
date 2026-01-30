package com.example.lestock.controller.dto.product;

public record GetProductMaterialDTO(
        ProductMaterialSummaryDTO material,
        Double quantity,
        Double totalCost
) {
}
