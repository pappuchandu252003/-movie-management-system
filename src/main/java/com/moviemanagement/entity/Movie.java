package com.moviemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private Integer duration; // in minutes

    private String language;

    private LocalDate releaseDate;

    private Double rating;

    private String genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Show> shows;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews;
}