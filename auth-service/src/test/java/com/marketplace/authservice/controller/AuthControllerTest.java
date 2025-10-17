package com.marketplace.authservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.authservice.dto.AuthResponse;
import com.marketplace.authservice.dto.LoginDto;
import com.marketplace.authservice.dto.RegisterDto;
import com.marketplace.authservice.service.AuthService;
import com.marketplace.common.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    private RegisterDto  registerDto;
    private LoginDto loginDto;
    private AuthResponse authResponse;

    @BeforeEach
    public void setup() {
        registerDto = new RegisterDto("man", "1234", Role.USER);
        loginDto = new LoginDto("woman", "1234");
        authResponse = new AuthResponse("123456");
    }

    @Test
    public void register_shouldReturnOkWhenRegistered() throws Exception {
        when(authService.register(registerDto)).thenReturn(authResponse);
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());
        verify(authService, times(1)).register(registerDto);
    }



    @Test
    public void login_shouldReturnOkWhenLogin() throws Exception {
        when(authService.login(loginDto.getUsername(), loginDto.getPassword())).thenReturn(authResponse);
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());
        verify(authService, times(1)).login(loginDto.getUsername(), loginDto.getPassword());
    }

    @Test
    public void register_shouldReturnWhenBadRequest() throws Exception {
        registerDto.setUsername(null);
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest());
    }

}
