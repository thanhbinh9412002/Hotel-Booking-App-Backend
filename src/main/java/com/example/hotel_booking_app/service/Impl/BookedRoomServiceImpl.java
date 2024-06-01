package com.example.hotel_booking_app.service.Impl;

import com.example.hotel_booking_app.exception.InvalidBookedRequestException;
import com.example.hotel_booking_app.exception.ResourceNotFoundException;
import com.example.hotel_booking_app.model.BookedRoom;
import com.example.hotel_booking_app.model.Room;
import com.example.hotel_booking_app.repository.BookedRoomRepository;
import com.example.hotel_booking_app.service.IBookedRoomService;
import com.example.hotel_booking_app.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl implements IBookedRoomService {
    private final BookedRoomRepository bookedRepository;
    private final IRoomService roomService;
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookedRepository.findByRoomId(roomId);
    }

    @Override
    public List<BookedRoom> getAllBooked() {
        return bookedRepository.findAll();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookedRepository.findByBookingConfirmationCode(confirmationCode)
        .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :" + confirmationCode));
    }

    @Override
    public String saveBooked(Long roomId, BookedRoom bookedRequest) {
        if (bookedRequest.getCheckOutDate().isBefore(bookedRequest.getCheckInDate())) {
            throw new InvalidBookedRequestException("Check in date must come before check out date");
        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> bookedList = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookedRequest,bookedList);
        if(roomIsAvailable){
            room.addBooking(bookedRequest);
            bookedRepository.save(bookedRequest);
        }
        else {
            throw new InvalidBookedRequestException("Sorry, this room is not available for the selected dates");
        }
        return bookedRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookedRequest, List<BookedRoom> bookedList) {
        return bookedList.stream()
                .noneMatch(existingBooking ->
                        bookedRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookedRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookedRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookedRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookedRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookedRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookedRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookedRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookedRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookedRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookedRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookedRequest.getCheckOutDate().equals(bookedRequest.getCheckInDate()))
                );
    }

    @Override
    public void cancelBooked(Long bookedId) {
        bookedRepository.deleteById(bookedId);
    }

    @Override
    public List<BookedRoom> getBookedByUserEmail(String email) {
        return bookedRepository.findByGuestEmail(email);
    }
}
