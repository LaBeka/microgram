package com.example.microgram.controller;

import com.example.microgram.dto.ErrorDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService service;

    @GetMapping(value = "/findUserEmail/{email}")
    public ResponseEntity findUserByEmail(String email){
        ArrayList<String> validateErrors = new ArrayList<>();
        Optional<User> userFound = service.getUserByEmail(email);
        if(!userFound.isPresent()){
            validateErrors.add("User by this email not found");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/findUserAccount/{accountName}")
    public ResponseEntity findUserByAccountName(@PathVariable String accountName){
        ArrayList<String> validateErrors = new ArrayList<>();
        Optional<User> userFound = service.getUserByAccountName(accountName);
        if(!userFound.isPresent()){
            validateErrors.add("User by this account name not found");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/findUserName/{userName}")
    public ResponseEntity findUserByName(@PathVariable String userName){
        ArrayList<String> validateErrors = new ArrayList<>();
        Optional<User> userFound = service.getUserByName(userName);
        if(!userFound.isPresent()){
            validateErrors.add("User by this name not found");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @GetMapping("/deleteUser/{email}")
    public ResponseEntity deleteUserByEmail(@PathVariable String email){
        ArrayList<String> validateErrors= new ArrayList<>();
        Optional<User> userFound = service.deleteUserByEmail(email);
        if(!userFound.isPresent()){
            validateErrors.add("User by this email is not found");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteAllUsers")
    public ResponseEntity deleteAll() throws SQLException {
        return new ResponseEntity<String>(service.deleteAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/showUsers")
    public ResponseEntity getUsers(){
        List<User> listUsers = service.getUsers();
        ArrayList<String> validateErrors = new ArrayList<>();
        if(listUsers.size() == 0){
            validateErrors.add("There is no users");
            return new ResponseEntity<ErrorDto>(new ErrorDto(validateErrors), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<User>>(listUsers, HttpStatus.OK);
    }
}
