package com.example.demo.entities.user;

import com.example.demo.exceptions.ValidationException;

public class RegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    /**
     *
     * @param parts -> read from the console
     */
    public RegisterDTO(String[] parts) {
        this.email = parts[1];
        this.password = parts[2];
        this.confirmPassword = parts[3];
        this.fullName = parts[4];

        this.validate();
    }

    private void validate() {
        if(!this.email.contains("@") || !this.email.contains(".") ){
            throw new ValidationException("Email must be validated properly!");
        }

        if(!this.password.matches("[A-Z][a-z]+[0-9]+[a-z]*")){
            throw new ValidationException("Password must be validated properly!");
        }

        if(!this.password.equals(this.confirmPassword)){
            throw new ValidationException("Confirm Password must be equal to the original password!");
        }
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
