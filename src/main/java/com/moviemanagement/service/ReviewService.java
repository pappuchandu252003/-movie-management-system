package com.moviemanagement.service;

import com.moviemanagement.dto.ReviewRequestDTO;
import com.moviemanagement.dto.ReviewResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO addReview(ReviewRequestDTO requestDTO);

    ReviewResponseDTO updateReview(Integer id, ReviewRequestDTO requestDTO, String username);

    void deleteReview(Integer id, String username);

    Page<ReviewResponseDTO> getAllReviews(int page, int size, String sortBy);

    //  Reviews by Movie (USER)
    Page<ReviewResponseDTO> getReviewsByMovie(Integer movieId, int page, int size, String sortBy);
}