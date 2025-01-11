package com.example.hotelbnmproject.strategy;

import com.example.hotelbnmproject.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{
    private final PricingStrategy wrappedOn;

    private final BigDecimal holidaySurgeFactor = BigDecimal.valueOf(1.1);
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrappedOn.calculatePrice(inventory);

        boolean isHoliday =false ;
        //TODO: use some API or maintain a list of All Holidays in local system and check if its a holiday
        if (isHoliday){
            price = price.multiply(holidaySurgeFactor);
        }
        return price;
    }
}
