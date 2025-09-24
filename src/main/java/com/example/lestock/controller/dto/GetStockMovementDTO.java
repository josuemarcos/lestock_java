package com.example.lestock.controller.dto;

import com.example.lestock.model.MovementType;

import java.time.LocalDate;

public record GetStockMovementDTO(
        Long id,
        MovementType movementType,
        Float quantity,
        Float unitPrice,
        LocalDate movementDate,
        GetStockDTO stock,
        SupplierDTO supplier
) {
}
