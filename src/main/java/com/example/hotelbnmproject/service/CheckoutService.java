package com.example.hotelbnmproject.service;

import com.example.hotelbnmproject.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
