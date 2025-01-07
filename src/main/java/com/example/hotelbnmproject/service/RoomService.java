package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.RoomDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    @Transactional
    void deleteRoomById(Long roomId);
}
