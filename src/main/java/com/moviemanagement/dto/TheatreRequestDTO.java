package com.moviemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TheatreRequestDTO {

    @NotBlank(message = "Theatre name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;
}