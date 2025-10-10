package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public List<UserResponseDto> findAll(@RequestHeader Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        return userService.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserDto user) {

        System.out.println(user.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }
}
