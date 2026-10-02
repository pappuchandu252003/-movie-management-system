package com.moviemanagement.service.impl;

import com.moviemanagement.dto.TheatreRequestDTO;
import com.moviemanagement.dto.TheatreResponseDTO;
import com.moviemanagement.entity.Theatre;
import com.moviemanagement.exception.ResourceNotFoundException;
import com.moviemanagement.repository.TheatreRepository;
import com.moviemanagement.service.TheatreService;
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
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    // 🔹 ADD THEATRE
    @Override
    public TheatreResponseDTO addTheatre(TheatreRequestDTO requestDTO) {

        Theatre theatre = new Theatre();
        theatre.setName(requestDTO.getName());
        theatre.setLocation(requestDTO.getLocation());

        Theatre saved = theatreRepository.save(theatre);

        return mapToResponse(saved);
    }

    // 🔹 UPDATE THEATRE
    @Override
    public TheatreResponseDTO updateTheatre(Integer id, TheatreRequestDTO requestDTO) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));

        theatre.setName(requestDTO.getName());
        theatre.setLocation(requestDTO.getLocation());

        Theatre updated = theatreRepository.save(theatre);

        return mapToResponse(updated);
    }

    // 🔹 DELETE THEATRE
    @Override
    public void deleteTheatre(Integer id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));

        theatreRepository.delete(theatre);
    }

    // 🔹 GET THEATRE BY ID
    @Override
    public TheatreResponseDTO getTheatreById(Integer id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre not found"));

        return mapToResponse(theatre);
    }

    // 🔹 GET ALL THEATRES
    @Override
    public Page<TheatreResponseDTO> getAllTheatres(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Theatre> theatrePage = theatreRepository.findAll(pageable);

        return theatrePage.map(this::mapToDTO);
    }

    // 🔹 ENTITY → DTO
    private TheatreResponseDTO mapToResponse(Theatre theatre) {

        TheatreResponseDTO dto = new TheatreResponseDTO();
        dto.setId(theatre.getId());
        dto.setName(theatre.getName());
        dto.setLocation(theatre.getLocation());

        return dto;
    }
    private TheatreResponseDTO mapToDTO(Theatre theatre) {

        TheatreResponseDTO dto = new TheatreResponseDTO();

        dto.setId(theatre.getId());
        dto.setName(theatre.getName());
        dto.setLocation(theatre.getLocation());


        return dto;
    }

}