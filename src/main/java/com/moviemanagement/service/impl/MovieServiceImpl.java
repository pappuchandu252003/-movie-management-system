package com.moviemanagement.service.impl;

import com.moviemanagement.dto.MovieRequestDTO;
import com.moviemanagement.dto.MovieResponseDTO;
import com.moviemanagement.entity.Movie;
import com.moviemanagement.exception.ResourceNotFoundException;
import com.moviemanagement.repository.MovieRepository;
import com.moviemanagement.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    //  ADD MOVIE
    @Override
    public MovieResponseDTO addMovie(MovieRequestDTO requestDTO) {

        Movie movie = new Movie();
        movie.setTitle(requestDTO.getTitle());
        movie.setDescription(requestDTO.getDescription());
        movie.setDuration(requestDTO.getDuration());
        movie.setLanguage(requestDTO.getLanguage());
        movie.setReleaseDate(requestDTO.getReleaseDate());
        movie.setRating(requestDTO.getRating());
        movie.setGenre(requestDTO.getGenre());

        Movie savedMovie = movieRepository.save(movie);

        return mapToResponse(savedMovie);
    }

    //  UPDATE MOVIE
    @Override
    public MovieResponseDTO updateMovie(Integer id, MovieRequestDTO requestDTO) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setTitle(requestDTO.getTitle());
        movie.setDescription(requestDTO.getDescription());
        movie.setDuration(requestDTO.getDuration());
        movie.setLanguage(requestDTO.getLanguage());
        movie.setReleaseDate(requestDTO.getReleaseDate());
        movie.setRating(requestDTO.getRating());
        movie.setGenre(requestDTO.getGenre());

        Movie updatedMovie = movieRepository.save(movie);

        return mapToResponse(updatedMovie);
    }

    //  DELETE MOVIE
    @Override
    public void deleteMovie(Integer id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movieRepository.delete(movie);
    }

    //  GET MOVIE BY ID
    @Override
    public MovieResponseDTO getMovieById(Integer id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        return mapToResponse(movie);
    }

    //  GET ALL MOVIES
    @Override
    public Page<MovieResponseDTO> getAllMovies(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Movie> moviePage = movieRepository.findAll(pageable);

        return moviePage.map(this::mapToDTO);
    }
    // 🔹 GET MOVIES BY GENRE
    @Override
    public List<MovieResponseDTO> getMoviesByGenre(String genre) {

        return movieRepository.findByGenre(genre)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //  ENTITY → DTO
    private MovieResponseDTO mapToResponse(Movie movie) {

        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setDuration(movie.getDuration());
        dto.setLanguage(movie.getLanguage());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setRating(movie.getRating());
        dto.setGenre(movie.getGenre());

        return dto;
    }
    private MovieResponseDTO mapToDTO(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setDuration(movie.getDuration());
        dto.setRating(movie.getRating());
        return dto;
    }
}