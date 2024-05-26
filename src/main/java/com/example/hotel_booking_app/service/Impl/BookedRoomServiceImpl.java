package com.example.hotel_booking_app.service.Impl;

import com.example.hotel_booking_app.model.BookedRoom;
import com.example.hotel_booking_app.repository.BookedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl {
    private final BookedRoomRepository bookingRepository;
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }
}
