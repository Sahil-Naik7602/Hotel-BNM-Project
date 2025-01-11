package com.example.hotelbnmproject.dto;

import com.example.hotelbnmproject.entity.User;
import com.example.hotelbnmproject.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
