package com.moviemanagement.controller;

import com.moviemanagement.dto.UserRequestDTO;
import com.moviemanagement.dto.UserResponseDTO;
import com.moviemanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 🔹 REGISTER USER
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return userService.registerUser(requestDTO);
    }

    // 🔹 GET ALL USERS (ADMIN)
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // 🔹 GET USER BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // 🔹 DELETE USER
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}