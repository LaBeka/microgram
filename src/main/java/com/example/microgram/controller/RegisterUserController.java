package com.example.microgram.controller;

import com.example.microgram.dto.user.RegUserFrontDto;
import com.example.microgram.dto.user.RegisterUserDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterUserController {
    private RegistrationService service;

    @PostMapping(value="/reg")
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity register(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("name") String name) {
        RegisterUserDto regUser = new RegisterUserDto(email, password, name);
        String validateErrors = regUser.validateUserData();
        if (!validateErrors.isEmpty()) {
            return new ResponseEntity(validateErrors, HttpStatus.OK);
        }
        Optional<RegUserFrontDto> userExists = service.checkIfUserExists(regUser);
        if (userExists.isPresent()) {
            return new ResponseEntity("User with this email and account name already exists", HttpStatus.OK);
        }
        Optional<RegUserFrontDto> createdUser = service.registerNewUser(regUser);
        if(createdUser.isEmpty()){
            return new ResponseEntity("For some reason user is not created", HttpStatus.OK);
        }
        return new ResponseEntity(createdUser.get(), HttpStatus.OK);
    }
}
