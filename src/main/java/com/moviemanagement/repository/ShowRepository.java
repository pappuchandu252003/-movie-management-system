package com.moviemanagement.repository;

import com.moviemanagement.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    List<Show> findByMovieId(Integer movieId);

    List<Show> findByTheatreId(Integer theatreId);
}