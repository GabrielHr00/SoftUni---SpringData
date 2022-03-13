package com.example.demo.services;

import com.example.demo.LogInDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.user.RegisterDTO;
import com.example.demo.exceptions.UserNotLoggedInException;
import com.example.demo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private User currentUser;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.currentUser = null;
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerData) {
        ModelMapper mapper = new ModelMapper();
        User toRegister = mapper.map(registerData, User.class);

        long countUsers = this.userRepository.count();
        if(countUsers == 0){
            toRegister.setAdmin(true);
        }

        return this.userRepository.save(toRegister);
    }

    @Override
    public Optional<User> login(LogInDTO loginData) {
        Optional<User> user =  this.userRepository.
                findByEmailAndPassword(loginData.getEmail(), loginData.getPassword());

        user.ifPresent(value -> this.currentUser = value);
        return user;
    }

    @Override
    public User getLoggedUser() {
        if(this.currentUser == null){
            throw new UserNotLoggedInException("No users logged in!");
        }
        return this.currentUser;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    @Override
    public void logout() {
        if(currentUser != null){
            currentUser = null;
        }
    }
}
