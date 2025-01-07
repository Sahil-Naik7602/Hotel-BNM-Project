package com.example.hotelbnmproject.repository;

import com.example.hotelbnmproject.entity.Inventory;
import com.example.hotelbnmproject.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    public void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
