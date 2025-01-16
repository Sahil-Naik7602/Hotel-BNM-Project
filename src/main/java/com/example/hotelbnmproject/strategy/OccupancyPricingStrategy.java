package com.example.hotelbnmproject.strategy;

import com.example.hotelbnmproject.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy{
    private final PricingStrategy wrappedOn;

    private final BigDecimal occupancySurgeFactor = BigDecimal.valueOf(1.2);
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrappedOn.calculatePrice(inventory);
        double occupancyRate = (double) inventory.getBookedCount() /inventory.getTotalCount();
        if (occupancyRate>0.8){
            price = price.multiply(occupancySurgeFactor);
        }
        return price;
    }
}
