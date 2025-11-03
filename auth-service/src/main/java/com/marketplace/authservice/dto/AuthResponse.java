package com.marketplace.authservice.dto;

public record AuthResponse(String token, String refreshToken) {
}
