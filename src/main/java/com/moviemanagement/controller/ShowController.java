package com.moviemanagement.controller;

import com.moviemanagement.dto.ShowRequestDTO;
import com.moviemanagement.dto.ShowResponseDTO;
import com.moviemanagement.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    // 🔹 ADD SHOW
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShowResponseDTO addShow(@Valid @RequestBody ShowRequestDTO requestDTO) {
        return showService.addShow(requestDTO);
    }

    // 🔹 UPDATE SHOW
    @PutMapping("/{id}")
    public ShowResponseDTO updateShow(@PathVariable Integer id,
                                      @RequestBody ShowRequestDTO requestDTO) {
        return showService.updateShow(id, requestDTO);
    }

    // 🔹 DELETE SHOW
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShow(@PathVariable Integer id) {
        showService.deleteShow(id);
    }

    // 🔹 GET SHOW BY ID
    @GetMapping("/{id}")
    public ShowResponseDTO getShowById(@PathVariable Integer id) {
        return showService.getShowById(id);
    }

    // 🔹 GET SHOWS BY MOVIE
    @GetMapping("/movie/{movieId}")
    public List<ShowResponseDTO> getShowsByMovie(@PathVariable Integer movieId) {
        return showService.getShowsByMovie(movieId);
    }

    // 🔹 GET SHOWS BY THEATRE
    @GetMapping("/theatre/{theatreId}")
    public List<ShowResponseDTO> getShowsByTheatre(@PathVariable Integer theatreId) {
        return showService.getShowsByTheatre(theatreId);
    }
    @GetMapping
    public ResponseEntity<Page<ShowResponseDTO>> getAllShows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(showService.getAllShows(page, size, sortBy));
    }
}