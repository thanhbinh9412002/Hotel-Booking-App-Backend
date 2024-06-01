package com.example.hotel_booking_app.exception;

public class InvalidBookedRequestException extends RuntimeException{
    public InvalidBookedRequestException(String message) {
        super(message);
    }
}
