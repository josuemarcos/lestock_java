package com.example.lestock.controller.dto;

public record SaveStockDTO(
                           Float currentQuantity,
                           Float averageCost,
                           Long materialTypeId
                       ) {
}
