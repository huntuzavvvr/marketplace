package com.marketplace.authservice.service;

import com.marketplace.authservice.dto.*;
import com.marketplace.common.utils.JwtTokenUtil;
import com.marketplace.authservice.entity.UserEntity;
import com.marketplace.authservice.mapper.UserMapper;
import com.marketplace.authservice.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final VerificationServiceImpl verificationServiceImpl;

    public AuthResponse register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        String token = jwtTokenUtil.generateToken(registerDto.getUsername(), registerDto.getRole().name());
        String refreshToken = jwtTokenUtil.generateRefreshToken(registerDto.getUsername(),  registerDto.getRole().name());
        AuthResponse response = new AuthResponse(token, refreshToken);

        UserEntity userEntity = userMapper.toEntity(registerDto);

        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        String verifyToken = UUID.randomUUID().toString();
//        verificationServiceImpl.sendVerificationCode(userEntity.getEmail(), verifyToken);

        userRepository.save(userEntity);

        return response;
    }

    public AuthResponse login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if  (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password");
        }

        String refreshToken =  jwtTokenUtil.generateRefreshToken(username, "ADMIN");
        String token = jwtTokenUtil.generateToken(username, "ADMIN");
        AuthResponse response = new AuthResponse(token, refreshToken);

        return response;
    }

    public AuthResponse verifyEmail(String code) {
        return null;
    }

    public RefreshResponseDto refreshToken(RefreshDto refreshDto) {
        String refreshToken = refreshDto.getRefreshToken();
        if (!jwtTokenUtil.validateToken(refreshDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Refresh Token");
        }
        String username = jwtTokenUtil.extractUsername(refreshToken);
        String role = jwtTokenUtil.extractRole(refreshToken);
        String accessToken = jwtTokenUtil.generateToken(username, role);
        return new RefreshResponseDto(accessToken);
    }
}
