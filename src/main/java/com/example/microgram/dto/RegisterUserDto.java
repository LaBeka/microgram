package com.example.microgram.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class RegisterUserDto {
    private String accountName;
    private String email;
    private String password;
    private String userName;

    public ArrayList<String> validateRegUser(){
        ArrayList<String> errors = new ArrayList<>();
        if(accountName.isBlank() || accountName == null || accountName.length() < 2){
            errors.add("Account name is not valid");
        }
        if(email.isBlank() || email == null || email.length() < 5){
            errors.add("Email is not valid.");
        }
        if(password.isEmpty() || password.length() < 5 || password == null){
            errors.add("Password is not valid");
        }
        if(userName.isEmpty() || userName.length() < 3 || userName == null){
            errors.add("User name is not valid");
        }
        return errors;
    }

}
