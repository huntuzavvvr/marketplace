package com.marketplace.authservice.controller;

import com.marketplace.authservice.dto.*;
import com.marketplace.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterDto registerDto) {
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto.getUsername(), loginDto.getPassword()));
    }

    @PostMapping("api/auth/logout")
    public ResponseEntity<AuthResponse> logout(@RequestBody @Valid LoginDto loginDto) {return null;}

    @PostMapping("api/auth/refresh-token")
    public ResponseEntity<RefreshResponseDto> refreshToken(@RequestBody @Valid RefreshDto refreshDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshDto));
    }

//    @PostMapping("/api/auth/verify-email")
//    public ResponseEntity<AuthResponse> verifyEmail() {
//        return ResponseEntity.ok(authService.verifyEmail(loginDto));
//    }
}
