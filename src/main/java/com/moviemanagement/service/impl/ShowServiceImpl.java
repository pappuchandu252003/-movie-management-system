package com.moviemanagement.service.impl;

import com.moviemanagement.dto.ShowRequestDTO;
import com.moviemanagement.dto.ShowResponseDTO;
import com.moviemanagement.entity.Movie;
import com.moviemanagement.entity.Show;
import com.moviemanagement.entity.Theatre;
import com.moviemanagement.exception.ResourceNotFoundException;
import com.moviemanagement.repository.MovieRepository;
import com.moviemanagement.repository.ShowRepository;
import com.moviemanagement.repository.TheatreRepository;
import com.moviemanagement.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;

    // 🔹 ADD SHOW
    @Override
    public ShowResponseDTO addShow(ShowRequestDTO requestDTO) {

        Movie movie = movieRepository.findById(requestDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theatre theatre = theatreRepository.findById(requestDTO.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowTime(requestDTO.getShowTime());
        show.setPrice(requestDTO.getPrice());

        Show saved = showRepository.save(show);

        return mapToResponse(saved);
    }

    // 🔹 UPDATE SHOW
    @Override
    public ShowResponseDTO updateShow(Integer id, ShowRequestDTO requestDTO) {

        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        Movie movie = movieRepository.findById(requestDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Theatre theatre = theatreRepository.findById(requestDTO.getTheatreId())
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));

        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowTime(requestDTO.getShowTime());
        show.setPrice(requestDTO.getPrice());

        Show updated = showRepository.save(show);

        return mapToResponse(updated);
    }

    // 🔹 DELETE SHOW
    @Override
    public void deleteShow(Integer id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        showRepository.delete(show);
    }

    // 🔹 GET SHOW BY ID
    @Override
    public ShowResponseDTO getShowById(Integer id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        return mapToResponse(show);
    }

    // 🔹 GET SHOWS BY MOVIE
    @Override
    public List<ShowResponseDTO> getShowsByMovie(Integer movieId) {

        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 GET SHOWS BY THEATRE
    @Override
    public List<ShowResponseDTO> getShowsByTheatre(Integer theatreId) {

        return showRepository.findByTheatreId(theatreId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 ENTITY → DTO
    private ShowResponseDTO mapToResponse(Show show) {

        ShowResponseDTO dto = new ShowResponseDTO();

        dto.setId(show.getId());

        dto.setMovieId(show.getMovie().getId());
        dto.setMovieTitle(show.getMovie().getTitle());

        dto.setTheatreId(show.getTheatre().getId());
        dto.setTheatreName(show.getTheatre().getName());

        dto.setShowTime(show.getShowTime());
        dto.setPrice(show.getPrice());

        return dto;
    }

    @Override
    public Page<ShowResponseDTO> getAllShows(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Show> showPage = showRepository.findAll(pageable);

        return showPage.map(this::mapToDTO);
    }

    //  MAPPING METHOD
    private ShowResponseDTO mapToDTO(Show show) {

        ShowResponseDTO dto = new ShowResponseDTO();

        dto.setId(show.getId());

        //  IMPORTANT: handle relationships properly

        dto.setTheatreName(show.getTheatre().getName());

        dto.setShowTime(show.getShowTime());
        dto.setPrice(show.getPrice());

        return dto;
    }
}