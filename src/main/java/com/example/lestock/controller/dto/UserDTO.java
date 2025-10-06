package com.example.lestock.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserDTO(
        Long id,
        @NotNull(message = "Can't be blank")
        String userName,
        @NotNull(message = "Can't be blank")
        String password,
        @NotNull(message = "Can't be blank")
        @Email(message = "Invalid e-mail")
        String email,
        List<String> roles
) {
}
