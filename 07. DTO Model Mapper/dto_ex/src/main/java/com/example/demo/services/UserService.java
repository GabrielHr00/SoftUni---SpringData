package com.example.demo.services;

import com.example.demo.LogInDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.user.RegisterDTO;

import java.util.Optional;

public interface UserService {
    User register(RegisterDTO registerData);

    Optional<User> login(LogInDTO loginData);

    User getLoggedUser();

    void logout();
}
