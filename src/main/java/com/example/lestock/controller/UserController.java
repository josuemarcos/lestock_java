package com.example.lestock.controller;

import com.example.lestock.controller.dto.UserDTO;
import com.example.lestock.controller.mapper.UserMapper;
import com.example.lestock.model.User;
import com.example.lestock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userService.saveUser(user);
        URI location = generateHeaderLocation(user.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> users = userService
                .findAllUsers()
                .stream()
                .map(userMapper::toDTO)
                .toList();
        return ResponseEntity.ok(users);
    }
}
