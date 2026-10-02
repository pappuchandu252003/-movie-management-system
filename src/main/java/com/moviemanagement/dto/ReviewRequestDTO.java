package com.moviemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequestDTO {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Movie ID is required")
    private Integer movieId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @NotBlank(message = "Comment is required")
    private String comment;
}