package com.example.hotelbnmproject.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchDto {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;

    private Integer pageNumber = 0;
    private Integer size = 10;
}
