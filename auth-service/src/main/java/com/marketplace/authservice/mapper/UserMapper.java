package com.marketplace.authservice.mapper;

import com.marketplace.authservice.dto.RegisterDto;
import com.marketplace.authservice.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(RegisterDto registerDto);
}
