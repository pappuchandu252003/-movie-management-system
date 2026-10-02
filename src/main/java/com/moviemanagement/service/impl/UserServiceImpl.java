package com.moviemanagement.service.impl;

import com.moviemanagement.dto.UserRequestDTO;
import com.moviemanagement.dto.UserResponseDTO;
import com.moviemanagement.entity.User;
import com.moviemanagement.exception.ResourceNotFoundException;
import com.moviemanagement.repository.UserRepository;
import com.moviemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // ✅ ADDED
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //  ADD THIS (VERY IMPORTANT)
    private final PasswordEncoder passwordEncoder;

    // 🔹 REGISTER USER
    @Override
    public UserResponseDTO registerUser(UserRequestDTO requestDTO) {

        // check duplicate username
        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(requestDTO.getUsername());

        //  REMOVED plain password line
        // user.setPassword(requestDTO.getPassword());

        //  CORRECT: ALWAYS ENCODE PASSWORD
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        user.setRole(requestDTO.getRole());

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    // 🔹 GET ALL USERS
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 GET USER BY ID
    @Override
    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    // 🔹 DELETE USER
    @Override
    public void deleteUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    // 🔹 ENTITY → DTO
    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}