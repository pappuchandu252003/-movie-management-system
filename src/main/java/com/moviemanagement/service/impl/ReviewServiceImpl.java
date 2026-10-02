package com.moviemanagement.service.impl;

import com.moviemanagement.dto.ReviewRequestDTO;
import com.moviemanagement.dto.ReviewResponseDTO;
import com.moviemanagement.entity.Movie;
import com.moviemanagement.entity.Review;
import com.moviemanagement.entity.User;
import com.moviemanagement.exception.ResourceNotFoundException;
import com.moviemanagement.exception.UnauthorizedException;
import com.moviemanagement.repository.MovieRepository;
import com.moviemanagement.repository.ReviewRepository;
import com.moviemanagement.repository.UserRepository;
import com.moviemanagement.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    //  ADD REVIEW
    @Override
    public ReviewResponseDTO addReview(ReviewRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Movie movie = movieRepository.findById(requestDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());

        Review saved = reviewRepository.save(review);

        return mapToDTO(saved);
    }

    //  UPDATE REVIEW
    @Override
    public ReviewResponseDTO updateReview(Integer id, ReviewRequestDTO requestDTO, String currentUsername) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        //  OWNERSHIP CHECK
        if (!review.getUser().getUsername().equals(currentUsername)) {
            throw new UnauthorizedException("You can only update your own review");
        }

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Movie movie = movieRepository.findById(requestDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        review.setUser(user);
        review.setMovie(movie);
        review.setRating(requestDTO.getRating());
        review.setComment(requestDTO.getComment());

        Review updated = reviewRepository.save(review);

        return mapToDTO(updated);
    }


    //  DELETE REVIEW
    @Override
    public void deleteReview(Integer id, String currentUsername) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        //  OWNERSHIP CHECK
        if (!review.getUser().getUsername().equals(currentUsername)) {
            throw new UnauthorizedException("You can only delete your own review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public Page<ReviewResponseDTO> getAllReviews(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Review> reviews = reviewRepository.findAll(pageable);

        return reviews.map(this::mapToDTO);
    }

    //  USER → REVIEWS BY MOVIE
    @Override
    public Page<ReviewResponseDTO> getReviewsByMovie(Integer movieId, int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Page<Review> reviews = reviewRepository.findByMovie(movie, pageable);

        return reviews.map(this::mapToDTO);
    }

    //  ENTITY → DTO
    //  ENTITY → DTO (FINAL CLEAN VERSION)
    private ReviewResponseDTO mapToDTO(Review review) {

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setId(review.getId());

        dto.setUserId(review.getUser().getId());
        dto.setUsername(review.getUser().getUsername());

        dto.setMovieId(review.getMovie().getId());
        dto.setMovieTitle(review.getMovie().getTitle()); // use correct field from Movie entity

        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        return dto;
    }

}