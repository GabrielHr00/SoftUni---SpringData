package com.example.demo.exceptions;

public class UserNotLoggedInException extends RuntimeException{

    public UserNotLoggedInException(String reason){
        super(reason);
    }
}
