package com.example.hotelbnmproject.repository;

import com.example.hotelbnmproject.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
