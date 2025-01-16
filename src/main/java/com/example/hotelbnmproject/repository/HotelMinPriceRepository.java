package com.example.hotelbnmproject.repository;

import com.example.hotelbnmproject.dto.HotelPriceDto;
import com.example.hotelbnmproject.entity.Hotel;
import com.example.hotelbnmproject.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice,Long> {

    @Query("""
            SELECT new com.example.hotelbnmproject.dto.HotelPriceDto(i.hotel,AVG(i.price))
            FROM HotelMinPrice i WHERE
                i.hotel.city = :city AND
                i.date BETWEEN :startDt AND :endDt AND
                i.hotel.active = true
            GROUP BY i.hotel
            """)
    public Page<HotelPriceDto> getAllValidInventoriesForDtRange(
            @Param("city") String city,
            @Param("startDt") LocalDate startDt,
            @Param("endDt") LocalDate endDt,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
