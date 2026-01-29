package com.example.lestock.controller.dto.product;
import com.example.lestock.controller.dto.stock.GetMaterialDTO;

public record GetProductMaterialDTO(
        GetMaterialDTO material,
        Double quantity,
        Double totalCost
) {
}
