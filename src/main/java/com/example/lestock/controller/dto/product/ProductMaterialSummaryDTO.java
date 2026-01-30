package com.example.lestock.controller.dto.product;

public record ProductMaterialSummaryDTO(
        Long id,
        String name,
        String metricUnit,
        Float pricePerAmount
) {
}
