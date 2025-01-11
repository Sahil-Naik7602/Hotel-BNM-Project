package com.example.hotelbnmproject.dto;

import com.example.hotelbnmproject.entity.Guest;
import com.example.hotelbnmproject.entity.Hotel;
import com.example.hotelbnmproject.entity.Room;
import com.example.hotelbnmproject.entity.User;
import com.example.hotelbnmproject.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;

}
