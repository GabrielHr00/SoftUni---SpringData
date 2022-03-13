package com.example.demo.services;

import com.example.demo.LogInDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.user.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExecuterServiceImpl implements ExecuterService{
    private UserService userService;

    @Autowired
    public ExecuterServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(String commandLine){
        String[] parts = commandLine.split("\\|");

        String commandName = parts[0];

        String commandOutput = switch (commandName) {
            case REGISTER_USER -> registerUser(parts);
            case LOGIN_USER -> loginUser(parts);
            case LOGOUT_USER -> logoutUser();
            default -> "unknown command";
        };

        return commandOutput;
    }

    private String logoutUser() {
        User loggedUser = userService.getLoggedUser();
        this.userService.logout();
        return String.format("User %s successfully logged out!", loggedUser.getFullName());
    }

    private String loginUser(String[] parts) {
        LogInDTO loginData = new LogInDTO(parts);
        Optional<User> toLogin = userService.login(loginData);

        if(toLogin.isPresent()){
            return String.format("Successfully logged in %s", toLogin.get().getFullName());
        }
        else{
            return "Wrong Credentials";
        }
    }

    private String registerUser(String[] parts) {
        RegisterDTO registerData = new RegisterDTO(parts);
        User user = userService.register(registerData);
        return String.format("%s was registered", user.getFullName());
    }
}
