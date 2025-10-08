package com.example.lestock.controller.dto;

public record ClientDTO(
        Long id,
        String clientId,
        String clientSecret,
        String redirectURI,
        String scope
) {
}
