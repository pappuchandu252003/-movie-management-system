package com.moviemanagement.service.impl;

import com.moviemanagement.dto.BookingRequestDTO;
import com.moviemanagement.dto.BookingResponseDTO;
import com.moviemanagement.entity.*;
import com.moviemanagement.exception.BadRequestException;
import com.moviemanagement.exception.ResourceNotFoundException;
import com.moviemanagement.exception.UnauthorizedException;
import com.moviemanagement.repository.BookingRepository;
import com.moviemanagement.repository.ShowRepository;
import com.moviemanagement.repository.UserRepository;
import com.moviemanagement.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    //  BOOK TICKET
    @Override
    public BookingResponseDTO bookTicket(BookingRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Show show = showRepository.findById(requestDTO.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(requestDTO.getNumberOfSeats());

        //  BUSINESS LOGIC
        double totalAmount = show.getPrice() * requestDTO.getNumberOfSeats();
        booking.setTotalAmount(totalAmount);

        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.BOOKED);

        Booking saved = bookingRepository.save(booking);

        return mapToResponse(saved);
    }

    // CANCEL BOOKING
    @Override
    public BookingResponseDTO cancelBooking(Integer bookingId, String currentUsername) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        //  OWNERSHIP CHECK
        if (!booking.getUser().getUsername().equals(currentUsername)) {
            throw new UnauthorizedException("You can only cancel your own booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Booking updated = bookingRepository.save(booking);

        return mapToResponse(updated);
    }

    @Override
    public Page<BookingResponseDTO> getUserBookings(int page, int size, String sortBy, String username) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<Booking> bookings = bookingRepository.findByUser(user, pageable);

        return bookings.map(this::mapToResponse);
    }

    //  ADMIN BOOKINGS
    @Override
    public Page<BookingResponseDTO> getAllBookings(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Booking> bookings = bookingRepository.findAll(pageable);

        return bookings.map(this::mapToResponse);
    }

    //  ENTITY → DTO
    private BookingResponseDTO mapToResponse(Booking booking) {

        BookingResponseDTO dto = new BookingResponseDTO();

        dto.setId(booking.getId());

        dto.setUserId(booking.getUser().getId());
        dto.setUsername(booking.getUser().getUsername());

        dto.setShowId(booking.getShow().getId());

        dto.setNumberOfSeats(booking.getNumberOfSeats());
        dto.setTotalAmount(booking.getTotalAmount());

        dto.setBookingDate(booking.getBookingDate());
        dto.setStatus(booking.getStatus());

        return dto;
    }
}