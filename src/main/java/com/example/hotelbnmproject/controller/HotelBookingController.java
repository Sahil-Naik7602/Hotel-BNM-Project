package com.example.hotelbnmproject.controller;

import com.example.hotelbnmproject.dto.BookingDto;
import com.example.hotelbnmproject.dto.BookingRequestDto;
import com.example.hotelbnmproject.dto.GuestDto;
import com.example.hotelbnmproject.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class HotelBookingController {
    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequestDto bookingRequestDto){
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequestDto));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDto> guestDtoList) {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
}
