package com.moviemanagement.dto;

import lombok.Data;

@Data
public class ReviewResponseDTO {

    private Integer id;

    private Integer userId;
    private String username;

    private Integer movieId;
    private String movieTitle;

    private Integer rating;
    private String comment;
}