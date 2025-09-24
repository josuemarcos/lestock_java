package com.example.lestock.controller.dto;

import com.example.lestock.model.MovementType;

import java.time.LocalDate;

public record SaveStockMovementDTO(
        MovementType movementType,
        Float quantity,
        Float unitPrice,
        LocalDate movementDate,
        Long stockId,
        Long supplierId
) {
}
