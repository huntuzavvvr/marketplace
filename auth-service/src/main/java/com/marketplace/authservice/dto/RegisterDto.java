package com.marketplace.authservice.dto;

import com.marketplace.common.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Role role;
}
