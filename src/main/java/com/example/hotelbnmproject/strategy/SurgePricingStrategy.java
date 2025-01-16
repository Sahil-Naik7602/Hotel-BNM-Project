package com.example.hotelbnmproject.strategy;

import com.example.hotelbnmproject.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy{
    private final PricingStrategy wrappedOn;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrappedOn.calculatePrice(inventory);
        return  price.multiply(inventory.getSurgeFactor());
    }
}
