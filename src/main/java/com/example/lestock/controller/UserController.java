package com.example.lestock.controller;

import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.UserDTO;
import com.example.lestock.controller.mapper.UserMapper;
import com.example.lestock.model.User;
import com.example.lestock.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Save user", description = "Register a new user to database")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userService.saveUser(user);
        URI location = generateHeaderLocation(user.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find all users", description = "Return all saved users")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> users = userService
                .findAllUsers()
                .stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Delete user", description = "Remove a saved user from database")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(
                        user -> {
                            userService.deleteUser(user);
                            return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
