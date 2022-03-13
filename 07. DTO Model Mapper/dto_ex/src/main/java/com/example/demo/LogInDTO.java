package com.example.demo;

public class LogInDTO {
    private String email;

    private String password;

    public LogInDTO(String[] parts) {
        this.email = parts[1];
        this.password = parts[2];
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
