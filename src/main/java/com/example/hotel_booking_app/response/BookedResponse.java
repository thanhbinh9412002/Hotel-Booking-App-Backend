package com.example.hotel_booking_app.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedResponse {
    private Long id;

    private LocalDate checkInDate;


    private LocalDate checkOutDate;


    private String guestFullName;


    private String guestEmail;


    private  int NumOfAdults;


    private  int NumOfChildren;


    private  int totalNumOfGuest;


    private String bookingConfirmationCode;

    private RoomResponse room;

    public BookedResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
