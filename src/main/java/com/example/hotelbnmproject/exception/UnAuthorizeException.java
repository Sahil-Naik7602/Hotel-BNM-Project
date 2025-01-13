package com.example.hotelbnmproject.exception;

public class UnAuthorizeException extends RuntimeException{
    public UnAuthorizeException(String message) {
        super(message);
    }
}
