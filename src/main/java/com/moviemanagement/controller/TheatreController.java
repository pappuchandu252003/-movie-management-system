package com.moviemanagement.controller;

import com.moviemanagement.dto.TheatreRequestDTO;
import com.moviemanagement.dto.TheatreResponseDTO;
import com.moviemanagement.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    // 🔹 ADD THEATRE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TheatreResponseDTO addTheatre(@Valid @RequestBody TheatreRequestDTO requestDTO) {
        return theatreService.addTheatre(requestDTO);
    }

    // 🔹 UPDATE THEATRE
    @PutMapping("/{id}")
    public TheatreResponseDTO updateTheatre(@PathVariable Integer id,
                                            @RequestBody TheatreRequestDTO requestDTO) {
        return theatreService.updateTheatre(id, requestDTO);
    }

    // 🔹 DELETE THEATRE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheatre(@PathVariable Integer id) {
        theatreService.deleteTheatre(id);
    }

    // 🔹 GET THEATRE BY ID
    @GetMapping("/{id}")
    public TheatreResponseDTO getTheatreById(@PathVariable Integer id) {
        return theatreService.getTheatreById(id);
    }

    // 🔹 GET ALL THEATRES
    @GetMapping
    public ResponseEntity<Page<TheatreResponseDTO>> getAllTheatres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(theatreService.getAllTheatres(page, size, sortBy));
    }
}