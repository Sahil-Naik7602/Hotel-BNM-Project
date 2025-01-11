package com.example.hotelbnmproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelInfoDto {
    public HotelDto hotel;
    public List<RoomDto> rooms;
}
