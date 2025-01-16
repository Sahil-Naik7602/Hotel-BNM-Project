package com.example.hotelbnmproject.dto;

import com.example.hotelbnmproject.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class HotelPriceDto {
    private Hotel hotel;
    private Double price;

    public HotelPriceDto(Hotel hotel, Double price) {
        this.hotel = hotel;
        this.price = price;
    }
}
