package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.BookingDto;
import com.example.hotelbnmproject.dto.BookingRequestDto;
import com.example.hotelbnmproject.dto.GuestDto;
import com.stripe.model.Event;

import java.util.List;

public interface BookingService {
    BookingDto initializeBooking(BookingRequestDto bookingRequestDto);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);

    String initiatePayment(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);
}
