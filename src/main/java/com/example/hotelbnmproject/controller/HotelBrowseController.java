package com.example.hotelbnmproject.controller;

import com.example.hotelbnmproject.dto.HotelDto;
import com.example.hotelbnmproject.dto.HotelInfoDto;
import com.example.hotelbnmproject.dto.HotelPriceDto;
import com.example.hotelbnmproject.dto.HotelSearchDto;
import com.example.hotelbnmproject.service.HotelService;
import com.example.hotelbnmproject.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/browse")
@RequiredArgsConstructor
@Slf4j
public class HotelBrowseController {
    private final InventoryService inventoryService;
    private final HotelService hotelService;


    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchDto hotelSearchDto){
        log.info("Inside searchHotels");
        Page<HotelPriceDto> page = inventoryService.searchHotels(hotelSearchDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        log.info("Inside getHotelInfo");
        HotelInfoDto response = hotelService.getHotelInfoById(hotelId);
        return ResponseEntity.ok(response);
    }

}
