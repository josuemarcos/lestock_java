package com.example.lestock.controller.dto.product;
import java.util.Set;

public record SaveProductDTO(
        String name,
        Double profitMargin,
        Set<SaveProductCostDTO> productCosts,
        Set<SaveProductMaterialDTO> productMaterials
) {
}
