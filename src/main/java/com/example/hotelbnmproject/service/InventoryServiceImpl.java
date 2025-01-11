package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.dto.HotelDto;
import com.example.hotelbnmproject.dto.HotelSearchDto;
import com.example.hotelbnmproject.entity.Hotel;
import com.example.hotelbnmproject.entity.Inventory;
import com.example.hotelbnmproject.entity.Room;
import com.example.hotelbnmproject.repository.HotelRepository;
import com.example.hotelbnmproject.repository.InventoryRepository;
import com.example.hotelbnmproject.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;


    @Override
    public void initializeRoomForAYear(Room room) {
        log.info("Inside initializeRoomForAYear");
        LocalDate today = LocalDate.now();
        LocalDate oneYearAheadDt = today.plusYears(1);
        while (today.isBefore(oneYearAheadDt)){
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .date(today)
                    .totalCount(room.getTotalCount())
                    .city(room.getHotel().getCity())
                    .surgeFactor(BigDecimal.ONE)
                    .price(room.getBasePrice())
                    .closed(false)
                    .bookedCount(0)
                    .build();

            today = today.plusDays(1);
            inventoryRepository.save(inventory);
        }
        log.info("Exiting initializeRoomForAYear");
    }

    @Override
    public void deleteAllInventories(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelDto> searchHotels(HotelSearchDto hotelSearchDto) {
        log.info("Inside searchHotels for city: "+hotelSearchDto.getCity()
                +" for date between "+hotelSearchDto.getStartDate()+" - "+hotelSearchDto.getEndDate());
        Pageable pageable = PageRequest.of(hotelSearchDto.getPageNumber(), hotelSearchDto.getSize());
        Long dateCount = ChronoUnit.DAYS.between(hotelSearchDto.getStartDate(),hotelSearchDto.getEndDate())+1;
        log.info("dateCount: "+dateCount);
        Page<Hotel> page =inventoryRepository.getAllValidInventoriesForDtRange(
                hotelSearchDto.getCity(),
                hotelSearchDto.getStartDate(),
                hotelSearchDto.getEndDate(),
                hotelSearchDto.getRoomsCount(),
                dateCount,
                pageable );

        return page.map(hotel -> modelMapper.map(hotel,HotelDto.class));
    }
}
