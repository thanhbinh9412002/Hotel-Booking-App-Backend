package com.example.hotel_booking_app.service;

import com.example.hotel_booking_app.model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
