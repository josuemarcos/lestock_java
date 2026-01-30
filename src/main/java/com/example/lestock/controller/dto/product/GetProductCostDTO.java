package com.example.lestock.controller.dto.product;

public record GetProductCostDTO(
        ProductCostSummaryDTO cost,
        Double quantity,
        Double totalCost
) {
}
