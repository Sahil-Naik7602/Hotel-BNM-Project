package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.entity.Inventory;
import com.example.hotelbnmproject.entity.Room;
import com.example.hotelbnmproject.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;


    @Override
    public void initializeRoomForAYear(Room room) {
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
                    .build();

            today = today.plusDays(1);
        }

    }

    @Override
    public void deleteFutureInventories(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today, room);
    }
}
