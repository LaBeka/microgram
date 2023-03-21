package com.example.microgram.controller;

import com.example.microgram.dto.ErrorDto;
import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@AllArgsConstructor
public class RegisterUserController {
    private RegistrationService service;

    @PostMapping(value = "/register")
    public ResponseEntity register(RegisterUserDto data) {
        ArrayList<String> validateErrors = data.validateRegUser();

        boolean userFound = service.findUserByEmail(data.getEmail());
        if(userFound == false){
            validateErrors.add("User by this email already exists.");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }

        if(validateErrors.size() > 0){
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }


        boolean recordUserDB = service.registerUser(data);

        if(!recordUserDB){
            validateErrors.add("User is not recorded in database.");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success!", HttpStatus.CREATED);
    }
}
