package com.moviemanagement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer duration;
    private String language;
    private LocalDate releaseDate;

    @DecimalMin(value = "0.0", message = "Rating must be >= 0")
    @DecimalMax(value = "5.0", message = "Rating must be <= 5")
    private Double rating;

    @NotBlank(message = "Genre is required")
    private String genre;
}