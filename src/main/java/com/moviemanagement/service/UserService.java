package com.moviemanagement.service;

import com.moviemanagement.dto.UserRequestDTO;
import com.moviemanagement.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO requestDTO);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Integer id);

    void deleteUser(Integer id);
}