package com.moviemanagement.dto;

import com.moviemanagement.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {

    private Integer id;

    private Integer userId;
    private String username;

    private Integer showId;

    private Integer numberOfSeats;
    private Double totalAmount;

    private LocalDateTime bookingDate;
    private BookingStatus status;
}