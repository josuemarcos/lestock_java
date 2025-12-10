package com.example.lestock.controller.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String userName,
        String password,
        String email,
        List<String> roles
) {
}
