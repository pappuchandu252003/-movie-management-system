package com.moviemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "theatres")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Theatre name is required")
    private String name;

    private String location;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Show> shows;
}