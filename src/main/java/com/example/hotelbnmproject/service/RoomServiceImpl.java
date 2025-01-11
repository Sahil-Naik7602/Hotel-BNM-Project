package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.RoomDto;
import com.example.hotelbnmproject.entity.Hotel;
import com.example.hotelbnmproject.entity.Room;
import com.example.hotelbnmproject.exception.ResourceNotFoundException;
import com.example.hotelbnmproject.repository.HotelRepository;
import com.example.hotelbnmproject.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto){
        log.info("Creating a room in hotel: "+hotelId);
        Hotel hotel =hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);

        roomRepository.save(room);

        inventoryService.initializeRoomForAYear(room);
        log.info("Exiting createNewRoom");
        return  modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId){
        log.info("Getting all the rooms in hotel with ID: {}", hotelId);
        Hotel hotel =hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public RoomDto getRoomById(Long roomId){
        log.info("Getting the room with ID: {}", roomId);
        Room room =roomRepository
                .findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found with ID: "+roomId));
        return modelMapper.map(room,RoomDto.class);
    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with ID: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }








}
