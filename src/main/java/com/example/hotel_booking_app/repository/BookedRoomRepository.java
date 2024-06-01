package com.example.hotel_booking_app.repository;

import com.example.hotel_booking_app.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomId(Long roomId);

    List<BookedRoom> findByGuestEmail(String email);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);
}
