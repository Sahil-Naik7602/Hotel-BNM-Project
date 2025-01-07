package com.example.hotelbnmproject.repository;

import com.example.hotelbnmproject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
