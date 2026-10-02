package com.moviemanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private Integer duration;
    private String language;
    private LocalDate releaseDate;
    private Double rating;
    private String genre;
}