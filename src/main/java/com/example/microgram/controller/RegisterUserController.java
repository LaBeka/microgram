package com.example.microgram.controller;

import com.example.microgram.dto.ErrorDto;
import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class RegisterUserController {
    private RegistrationService service;

    @PostMapping(value = "/")
    public ResponseEntity register(Model model, RegisterUserDto data) {
        ArrayList<String> validateErrors = data.validateRegUser();
        if(validateErrors.size() > 0){
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        Optional<User> userFound = service.findUserByEmail(data.getEmail());
        if(userFound.isPresent()){
            validateErrors.add("User by this email already exists.");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        boolean recordUserDB = service.registerUser(
                data.getAccountName(),
                data.getEmail(),
                data.getPassword(),
                data.getUserName());

        if(!recordUserDB){
            validateErrors.add("User is not recorded in database.");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Success!", HttpStatus.CREATED);
    }
}
