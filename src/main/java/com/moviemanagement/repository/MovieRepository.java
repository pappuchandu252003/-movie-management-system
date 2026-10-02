package com.moviemanagement.repository;

import com.moviemanagement.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByGenre(String genre);

    List<Movie> findByTitleContainingIgnoreCase(String title);
}