package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.HotelDto;
import com.example.hotelbnmproject.dto.HotelPriceDto;
import com.example.hotelbnmproject.dto.HotelSearchDto;
import com.example.hotelbnmproject.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    public void initializeRoomForAYear(Room room);

    public void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchDto hotelSearchDto);
}
