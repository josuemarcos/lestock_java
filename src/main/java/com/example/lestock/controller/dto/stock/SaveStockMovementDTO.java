package com.example.lestock.controller.dto.stock;
import com.example.lestock.model.stock.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record SaveStockMovementDTO(
        @NotNull(message = "Can't be blank")
        MovementType movementType,
        @NotNull(message = "Can't be blank")
        @Positive(message = "Value must be greater than zero")
        Float quantity,
        @Positive(message = "Value must be greater than zero")
        Float unitPrice,
        @PastOrPresent(message = "Date can't be in future")
        @NotNull(message = "Can't be blank")
        LocalDate movementDate,
        Long supplierId
) {
}
