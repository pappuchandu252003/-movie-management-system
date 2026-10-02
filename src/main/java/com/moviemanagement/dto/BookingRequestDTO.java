package com.moviemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookingRequestDTO {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Show ID is required")
    private Integer showId;

    @Min(value = 1, message = "At least 1 seat must be booked")
    private int numberOfSeats;
}