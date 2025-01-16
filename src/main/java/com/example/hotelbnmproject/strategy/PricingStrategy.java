package com.example.hotelbnmproject.strategy;

import com.example.hotelbnmproject.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public interface PricingStrategy {
    public BigDecimal calculatePrice(Inventory inventory);
}
