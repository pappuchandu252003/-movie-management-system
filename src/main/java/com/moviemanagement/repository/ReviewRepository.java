package com.moviemanagement.repository;

import com.moviemanagement.entity.Movie;
import com.moviemanagement.entity.Review;
import com.moviemanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findByMovie(Movie movie, Pageable pageable);

    List<Review> findByUser(User user);

}