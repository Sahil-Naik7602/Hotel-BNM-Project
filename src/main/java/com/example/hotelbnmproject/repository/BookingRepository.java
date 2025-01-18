package com.example.hotelbnmproject.repository;

import com.example.hotelbnmproject.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    public Optional<Booking> findByPaymentSessionId(String paymentSessionId);
}
