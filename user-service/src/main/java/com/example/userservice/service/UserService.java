package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    // нежелателен к использыванию
    // нежелателен к использыванию
    // нежелателен к использыванию скуф жирный
    // нежелателен к использыванию
    // нежелателен к использыванию
    // нежелателен к использыванию
    @Deprecated(since = "нежелателен к использыванию")
    public UserResponseDto register(UserDto user) {
        if (userRepository.existsByName(user.getName())) {
            throw new  ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setName(userDto.getName());
        return userMapper.toDto(userRepository.save(user));
    }
}
