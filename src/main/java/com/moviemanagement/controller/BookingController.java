package com.moviemanagement.controller;

import com.moviemanagement.dto.BookingRequestDTO;
import com.moviemanagement.dto.BookingResponseDTO;
import com.moviemanagement.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    //  BOOK TICKET
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO bookTicket(
            @Valid @RequestBody BookingRequestDTO requestDTO) {
        return bookingService.bookTicket(requestDTO);
    }

    // CANCEL BOOKING
    @PutMapping("/{id}/cancel")
    public BookingResponseDTO cancelBooking(
            @PathVariable Integer id,
            org.springframework.security.core.Authentication authentication
    ) {
        String username = authentication.getName();
        return bookingService.cancelBooking(id, username);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<BookingResponseDTO>> getUserBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            Authentication authentication
    ) {
        String username = authentication.getName();

        return ResponseEntity.ok(
                bookingService.getUserBookings(page, size, sortBy, username)
        );
    }

    //  ADMIN → View all bookings
    @GetMapping
    public ResponseEntity<Page<BookingResponseDTO>> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(
                bookingService.getAllBookings(page, size, sortBy)
        );
    }

}