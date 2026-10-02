package com.moviemanagement.repository;

import com.moviemanagement.entity.Booking;
import com.moviemanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    //List<Booking> findByUser(User user);
    Page<Booking> findByUser(User user, Pageable pageable);
}