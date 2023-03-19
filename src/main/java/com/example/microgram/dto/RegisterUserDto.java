package com.example.microgram.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RegisterUserDto {
    private String accountName;
    private String email;
    private String password;
    private String userName;

    public ArrayList<String> validate(){
        ArrayList<String> errors = new ArrayList<>();
        if(accountName.isBlank()){
            errors.add("Account name is not valid");
        }
        return errors;
    }
}
