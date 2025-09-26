package com.example.lestock.controller.dto;
import jakarta.validation.constraints.Positive;
public record SaveStockDTO(
        @Positive(message = "Can't be less than zero")
        Float currentQuantity,
        @Positive(message = "Can't be less than zero")
        Float averageCost
) {
}
