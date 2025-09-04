package com.example.lestock.controller.dto;

public record SupplierDTO(
        Long id,
        String name,
        String description,
        String contact,
        String socialMedia,
        String address
) {
}
