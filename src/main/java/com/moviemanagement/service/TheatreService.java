package com.moviemanagement.service;

import com.moviemanagement.dto.TheatreRequestDTO;
import com.moviemanagement.dto.TheatreResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TheatreService {

    TheatreResponseDTO addTheatre(TheatreRequestDTO requestDTO);

    TheatreResponseDTO updateTheatre(Integer id, TheatreRequestDTO requestDTO);

    void deleteTheatre(Integer id);

    TheatreResponseDTO getTheatreById(Integer id);

    Page<TheatreResponseDTO> getAllTheatres(int page, int size, String sortBy);
}