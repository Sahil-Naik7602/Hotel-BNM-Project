package com.example.hotelbnmproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotelBnmProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBnmProjectApplication.class, args);
	}

}
