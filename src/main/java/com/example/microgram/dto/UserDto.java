package com.example.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class UserDto {
    private int userId;
    private String accountName;
    private String email;
    private String password;
    private Integer postQuantity;
    private Integer followQuantity;
    private Integer followerQuantity;
    private String userName;

    public ArrayList<String> validateUser(){
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
        if(postQuantity < 0){
            errors.add("Post quantity can not be less then ZERO");
        }
        if(followQuantity < 0){
            errors.add("Followers quantity can not be less then ZERO");
        }
        return errors;
    }
}
