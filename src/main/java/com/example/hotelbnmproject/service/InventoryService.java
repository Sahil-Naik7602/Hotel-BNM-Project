package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.entity.Room;

public interface InventoryService {
    public void initializeRoomForAYear(Room room);

    public void deleteFutureInventories(Room room);
}
