package com.example.lestock.controller.dto.product;

public record GetProductCostDTO(
        CostDTO cost,
        Double quantity,
        Double totalCost
) {
}
