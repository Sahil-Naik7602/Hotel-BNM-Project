package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.BookingDto;
import com.example.hotelbnmproject.dto.BookingRequestDto;
import com.example.hotelbnmproject.dto.GuestDto;
import com.example.hotelbnmproject.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingService {
    BookingDto initializeBooking(BookingRequestDto bookingRequestDto);

    @Transactional
    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
