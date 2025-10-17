package com.marketplace.authservice.service;


import com.marketplace.authservice.dto.AuthResponse;
import com.marketplace.authservice.dto.LoginDto;
import com.marketplace.authservice.dto.RegisterDto;
import com.marketplace.authservice.entity.UserEntity;
import com.marketplace.authservice.mapper.UserMapper;
import com.marketplace.authservice.repository.UserRepository;
import com.marketplace.common.model.Role;
import com.marketplace.common.utils.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    private LoginDto loginDto;
    private RegisterDto registerDto;
    private UserEntity userEntity;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setUp() {
        loginDto = new LoginDto("stacy", "smith2005");
        registerDto = new RegisterDto("stacy", "smith2005", Role.USER);
        userEntity = new UserEntity(1L, "stacy", "smith2005", Role.USER);
    }

    @Test
    public void register_shouldReturnConflictWhenUserAlreadyExists() throws Exception {
        when(userRepository.findByUsername("stacy")).thenReturn(Optional.of(userEntity));

        var ex = assertThrows(ResponseStatusException.class, () -> authService.register(registerDto));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(userRepository, times(1)).findByUsername("stacy");
        verify(userRepository, times(0)).save(userEntity);
    }

    @Test
    public void register_shouldReturnAuthResponse() {
        when(jwtTokenUtil.generateToken("stacy", "USER")).thenReturn("1234");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toEntity(registerDto)).thenReturn(userEntity);
        AuthResponse result = authService.register(registerDto);

        assertEquals("1234", result.token());
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toEntity(registerDto);
        verify(jwtTokenUtil, times(1)).generateToken("stacy", "USER");
    }

    @Test
    public void login_shouldReturnAuthResponse() {
        when(userRepository.findByUsername("stacy")).thenReturn(Optional.of(userEntity));
        when(jwtTokenUtil.generateToken("stacy", "ADMIN")).thenReturn("1234");

        AuthResponse result = authService.login(loginDto.getUsername(), loginDto.getPassword());
        assertEquals("1234", result.token());
        verify(userRepository, times(1)).findByUsername("stacy");
        verify(userRepository, times(0)).save(userEntity);
    }

    @Test
    public void login_shouldReturnUnauthorizedWhenPasswordNotMatch() {
        when(userRepository.findByUsername("stacy")).thenReturn(Optional.of(userEntity));

        var ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginDto.getUsername(), "wrong"));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        verify(userRepository, times(1)).findByUsername("stacy");

    }

    @Test
    public void login_shouldReturnUnauthorizedWhenUsernameNotFound() {
        when(userRepository.findByUsername("stacy")).thenReturn(Optional.empty());
        var ex = assertThrows(ResponseStatusException.class, () -> authService.login(loginDto.getUsername(), loginDto.getPassword()));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
