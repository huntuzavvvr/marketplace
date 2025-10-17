package com.marketplace.authservice.service;

import com.marketplace.common.utils.JwtTokenUtil;
import com.marketplace.authservice.dto.AuthResponse;
import com.marketplace.authservice.dto.RegisterDto;
import com.marketplace.authservice.entity.UserEntity;
import com.marketplace.authservice.mapper.UserMapper;
import com.marketplace.authservice.repository.UserRepository;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public AuthResponse register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        AuthResponse response = new AuthResponse(jwtTokenUtil.generateToken(registerDto.getUsername(),
                registerDto.getRole().name()));
        userRepository.save(userMapper.toEntity(registerDto));
        return response;
    }

    public AuthResponse login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if  (!password.equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password");
        }

        AuthResponse response = new AuthResponse(jwtTokenUtil.generateToken(username, "ADMIN"));
        return response;
    }
}
