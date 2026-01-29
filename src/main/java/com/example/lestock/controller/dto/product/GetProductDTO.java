package com.example.lestock.controller.dto.product;
import java.util.Set;

public record GetProductDTO(
        String name,
        String price,
        Double profitMargin,
        Double materialCost,
        Double operationalCost,
        Set<GetProductCostDTO> productCosts,
        Set<GetProductMaterialDTO> materialCosts
) {
}
