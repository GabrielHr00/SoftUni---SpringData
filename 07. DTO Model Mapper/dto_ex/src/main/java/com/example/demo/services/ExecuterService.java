package com.example.demo.services;

public interface ExecuterService {
    String REGISTER_USER = "RegisterUser";
    String LOGIN_USER = "LoginUser";
    String LOGOUT_USER = "Logout";

    String execute(String command);
}
