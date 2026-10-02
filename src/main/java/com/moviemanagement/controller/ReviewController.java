package com.moviemanagement.controller;

import com.moviemanagement.dto.ReviewRequestDTO;
import com.moviemanagement.dto.ReviewResponseDTO;
import com.moviemanagement.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 🔹 ADD REVIEW
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDTO addReview(@Valid @RequestBody ReviewRequestDTO requestDTO) {
        return reviewService.addReview(requestDTO);
    }

    // 🔹 UPDATE REVIEW
    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(
            @PathVariable Integer id,
            @RequestBody ReviewRequestDTO requestDTO,
            org.springframework.security.core.Authentication authentication
    ) {
        String username = authentication.getName();
        return reviewService.updateReview(id, requestDTO, username);
    }

    // 🔹 DELETE REVIEW
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(
            @PathVariable Integer id,
            org.springframework.security.core.Authentication authentication
    ) {
        String username = authentication.getName();
        reviewService.deleteReview(id, username);
    }

    // 🔹 GET REVIEWS BY MOVIE
    @GetMapping
    public ResponseEntity<Page<ReviewResponseDTO>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(
                reviewService.getAllReviews(page, size, sortBy)
        );
    }

    // 🔹 USER → REVIEWS BY MOVIE
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Page<ReviewResponseDTO>> getReviewsByMovie(
            @PathVariable Integer movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(
                reviewService.getReviewsByMovie(movieId, page, size, sortBy)
        );
    }
}