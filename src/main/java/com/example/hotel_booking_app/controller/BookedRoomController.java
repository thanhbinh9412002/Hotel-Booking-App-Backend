package com.example.hotel_booking_app.controller;

import com.example.hotel_booking_app.exception.InvalidBookedRequestException;
import com.example.hotel_booking_app.exception.ResourceNotFoundException;
import com.example.hotel_booking_app.model.BookedRoom;
import com.example.hotel_booking_app.model.Room;
import com.example.hotel_booking_app.response.BookedResponse;
import com.example.hotel_booking_app.response.RoomResponse;
import com.example.hotel_booking_app.service.IBookedRoomService;
import com.example.hotel_booking_app.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173/")
//@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookedRoomController {
    private final IBookedRoomService bookedRoomService;
    private final IRoomService roomService;

    @GetMapping("/all-booked")
    public ResponseEntity<List<BookedResponse>> getAllBooked(){
        List<BookedRoom> bookedList = bookedRoomService.getAllBooked();
        List<BookedResponse> bookedResponses = new ArrayList<>();
        for(BookedRoom booked: bookedList){
            BookedResponse bookedResponse = getBookedResponse(booked);
            bookedResponses.add(bookedResponse);
        }
        return ResponseEntity.ok(bookedResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> findByBookingConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booked = bookedRoomService.findByBookingConfirmationCode(confirmationCode);
            BookedResponse bookedResponse = getBookedResponse(booked);
            return ResponseEntity.ok(bookedResponse);
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/user/{email}/booked")
    public ResponseEntity<List<BookedResponse>> getBookedByUserEmail(@PathVariable String email) {
        List<BookedRoom> bookedList = bookedRoomService.getBookedByUserEmail(email);
        List<BookedResponse> bookedResponses = new ArrayList<>();
        for (BookedRoom booked : bookedList) {
            BookedResponse bookedResponse = getBookedResponse(booked);
            bookedResponses.add(bookedResponse);
        }
        return ResponseEntity.ok(bookedResponses);
    }

    @PostMapping("/room/{roomId}/booked")
    public ResponseEntity<?> saveBooked(@PathVariable Long roomId,@RequestBody BookedRoom bookedRequest){
        try{
            String confirmationCode = bookedRoomService.saveBooked(roomId, bookedRequest);
            return ResponseEntity.ok("Room booked successfully, Your booked confirmation code is:"+confirmationCode);
        }catch(InvalidBookedRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/booked/{bookedId}/delete")
    public void cancelBooked(@PathVariable Long bookedId){
        bookedRoomService.cancelBooked(bookedId);
    }

    public BookedResponse getBookedResponse(BookedRoom booked){
        Room theRoom = roomService.getRoomById(booked.getRoom().getId()).get();
        RoomResponse roomResponse = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookedResponse(
                booked.getBookingId(),
                booked.getCheckInDate(),
                booked.getCheckOutDate(),
                booked.getGuestFullName(),
                booked.getGuestEmail(),
                booked.getNumOfAdults(),
                booked.getNumOfChildren(),
                booked.getTotalNumOfGuest(),
                booked.getBookingConfirmationCode(), roomResponse);
    }
}
