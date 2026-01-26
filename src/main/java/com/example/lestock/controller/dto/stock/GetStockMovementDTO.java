package com.example.lestock.controller.dto.stock;

import com.example.lestock.model.stock.MovementType;

import java.time.LocalDate;

public record GetStockMovementDTO(
        Long id,
        MovementType movementType,
        Float quantity,
        Float unitPrice,
        LocalDate movementDate,
        MaterialTypeDTO materialType,
        SupplierDTO supplier
) {
}
