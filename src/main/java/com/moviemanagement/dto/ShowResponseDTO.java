package com.moviemanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowResponseDTO {

    private Integer id;

    private Integer movieId;
    private String movieTitle;

    private Integer theatreId;
    private String theatreName;

    private LocalDateTime showTime;
    private Double price;
}