package com.moviemanagement.service;

import com.moviemanagement.dto.MovieRequestDTO;
import com.moviemanagement.dto.MovieResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    MovieResponseDTO addMovie(MovieRequestDTO requestDTO);

    MovieResponseDTO updateMovie(Integer id, MovieRequestDTO requestDTO);

    void deleteMovie(Integer id);

    MovieResponseDTO getMovieById(Integer id);

   // List<MovieResponseDTO> getAllMovies();

    List<MovieResponseDTO> getMoviesByGenre(String genre);
    Page<MovieResponseDTO> getAllMovies(int page, int size, String sortBy);
}