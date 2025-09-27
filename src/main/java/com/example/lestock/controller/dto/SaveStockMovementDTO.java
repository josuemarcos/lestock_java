package com.example.lestock.controller.dto;
import com.example.lestock.model.MovementType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SaveStockMovementDTO(
        @NotNull(message = "Can't be blank")
        MovementType movementType,
        @NotNull(message = "Can't be blank")
        Float quantity,
        @NotNull(message = "Can't be blank")
        Float unitPrice,
        @NotNull(message = "Can't be blank")
        LocalDate movementDate,
        Long supplierId
) {
}
