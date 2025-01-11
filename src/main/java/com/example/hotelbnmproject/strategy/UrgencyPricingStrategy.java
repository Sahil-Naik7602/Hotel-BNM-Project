package com.example.hotelbnmproject.strategy;

import com.example.hotelbnmproject.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy {
    private final PricingStrategy wrappedOn;
    private final BigDecimal urgencySurgeFactor = BigDecimal.valueOf(1.05);
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrappedOn.calculatePrice(inventory);
        LocalDate today = LocalDate.now();
        if (
                today.isAfter(inventory.getDate().minusDays(7)) ||
                today.isEqual(inventory.getDate().minusDays(7))
        ){
            price = price.multiply(urgencySurgeFactor);
        }
        return price;
    }
}
