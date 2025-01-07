package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.HotelDto;
import com.example.hotelbnmproject.entity.Hotel;
import com.example.hotelbnmproject.entity.Room;
import com.example.hotelbnmproject.exception.ResourceNotFoundException;
import com.example.hotelbnmproject.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements  HotelService {

    private final InventoryService inventoryService;
    private final RoomService roomService;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Inside createNewHotel");
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotelRepository.save(hotel);
        hotel.setActive(false);
        log.info("Exiting createNewHotel");
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with id: "+id+" not found."));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));


        for(Room room: hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomService.deleteRoomById(room.getId());
        }
        hotelRepository.deleteById(id);

    }

    @Override
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        hotel.setActive(true);

        // assuming only do it once
        for(Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }

    }
}
