package com.moviemanagement.controller;

import com.moviemanagement.dto.MovieRequestDTO;
import com.moviemanagement.dto.MovieResponseDTO;
import com.moviemanagement.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    //  ADD MOVIE (ADMIN)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDTO addMovie(@Valid @RequestBody MovieRequestDTO requestDTO) {
        return movieService.addMovie(requestDTO);
    }

    //  UPDATE MOVIE (ADMIN)
    @PutMapping("/{id}")
    public MovieResponseDTO updateMovie(@PathVariable Integer id,
                                        @RequestBody MovieRequestDTO requestDTO) {
        return movieService.updateMovie(id, requestDTO);
    }

    //  DELETE MOVIE (ADMIN)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
    }

    //  GET MOVIE BY ID
    @GetMapping("/{id}")
    public MovieResponseDTO getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    // GET ALL MOVIES
    @GetMapping
    public ResponseEntity<Page<MovieResponseDTO>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(movieService.getAllMovies(page, size, sortBy));
    }

    //  GET MOVIES BY GENRE
    @GetMapping("/genre/{genre}")
    public List<MovieResponseDTO> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }
}