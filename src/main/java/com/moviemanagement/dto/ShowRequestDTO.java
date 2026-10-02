package com.moviemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequestDTO {

    @NotNull(message = "Movie ID is required")
    private Integer movieId;

    @NotNull(message = "Theatre ID is required")
    private Integer theatreId;

    @NotNull(message = "Show time is required")
    private LocalDateTime showTime;

    @DecimalMin(value = "1.0", message = "Price must be greater than 0")
    private double price;
}