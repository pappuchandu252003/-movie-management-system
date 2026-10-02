package com.moviemanagement.service;

import com.moviemanagement.dto.ShowRequestDTO;
import com.moviemanagement.dto.ShowResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShowService {

    ShowResponseDTO addShow(ShowRequestDTO requestDTO);

    ShowResponseDTO updateShow(Integer id, ShowRequestDTO requestDTO);

    void deleteShow(Integer id);

    ShowResponseDTO getShowById(Integer id);

    List<ShowResponseDTO> getShowsByMovie(Integer movieId);

    List<ShowResponseDTO> getShowsByTheatre(Integer theatreId);
    Page<ShowResponseDTO> getAllShows(int page, int size, String sortBy);
}