package com.moviemanagement.service;

import com.moviemanagement.dto.BookingRequestDTO;
import com.moviemanagement.dto.BookingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    BookingResponseDTO bookTicket(BookingRequestDTO requestDTO);

    BookingResponseDTO cancelBooking(Integer bookingId, String username);
    //  USER → View their bookings
    Page<BookingResponseDTO> getUserBookings(int page, int size, String sortBy, String username);

    // ADMIN → View all bookings
    Page<BookingResponseDTO> getAllBookings(int page, int size, String sortBy);
}