package com.moviemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    private LocalDateTime showTime;

    private Double price;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}