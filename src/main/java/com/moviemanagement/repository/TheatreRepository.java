package com.moviemanagement.repository;

import com.moviemanagement.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
}