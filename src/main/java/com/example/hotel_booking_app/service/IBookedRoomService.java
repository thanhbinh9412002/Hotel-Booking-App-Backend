package com.example.hotel_booking_app.service;

import com.example.hotel_booking_app.model.BookedRoom;

import java.util.List;

public interface IBookedRoomService {
    List<BookedRoom> getAllBooked();

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    String saveBooked(Long roomId, BookedRoom bookedRequest);

    void cancelBooked(Long bookedId);

    List<BookedRoom> getBookedByUserEmail(String email);
}
