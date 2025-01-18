package com.example.hotelbnmproject.controller;

import com.example.hotelbnmproject.service.BookingService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    @Value("${stripe.webhook.key}")
    private String webHookKey;

    private final BookingService bookingService;


    @PostMapping("/payment")
    public ResponseEntity<Void> capturePayment(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader){

        try{
            Event event = Webhook.constructEvent(payload,sigHeader,webHookKey);
            bookingService.capturePayment(event);
            return ResponseEntity.noContent().build();
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }

    }

}
