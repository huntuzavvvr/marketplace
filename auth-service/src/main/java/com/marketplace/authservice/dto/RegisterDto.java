package com.marketplace.authservice.dto;

import com.marketplace.common.model.Role;
import lombok.Data;

@Data
public class RegisterDto {
    private String username;

    private String password;

    private Role role;
}
