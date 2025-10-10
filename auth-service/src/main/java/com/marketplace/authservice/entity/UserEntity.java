package com.marketplace.authservice.entity;

import com.marketplace.common.model.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
