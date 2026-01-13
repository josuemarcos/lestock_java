package com.example.lestock.controller;

import com.example.lestock.controller.dto.UserDTO;
import com.example.lestock.controller.dto.login.GoogleLoginDTO;
import com.example.lestock.controller.dto.login.LoginRequestDTO;
import com.example.lestock.controller.dto.login.ResponseTokenDTO;
import com.example.lestock.controller.mapper.UserMapper;
import com.example.lestock.model.User;
import com.example.lestock.security.CustomUserDetails;
import com.example.lestock.security.CustomUserDetailsService;
import com.example.lestock.security.GoogleTokenVerifierService;
import com.example.lestock.security.JwtService;
import com.example.lestock.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final GoogleTokenVerifierService googleTokenVerifierService;

    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.userName(),
                    loginRequestDTO.password()
                )
        );

        UserDetails authenticatedUser = (UserDetails) auth.getPrincipal();
        ResponseTokenDTO token = new ResponseTokenDTO(jwtService.generateToken(authenticatedUser));
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        UserDTO userDTO = userMapper.toDTO(user.getDomainUser());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/google")
    public ResponseEntity<ResponseTokenDTO> google(@RequestBody GoogleLoginDTO googleLoginDTO) {
        GoogleIdToken.Payload payload = googleTokenVerifierService.verify(googleLoginDTO.tokenId());

        return userService.findByEmail(payload.getEmail()).map(foundUser -> {
            ResponseTokenDTO token = new ResponseTokenDTO(jwtService.generateToken(new  CustomUserDetails(foundUser)));
            return ResponseEntity.ok(token);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
