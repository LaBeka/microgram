package com.example.microgram.controller;

import com.example.microgram.dto.ResultDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;

    @GetMapping(value = "/find/{email}")
    public ResponseEntity findUserByEmail(@PathVariable String email) {
        HashMap<String, Object> userFound = service.findUserByEmail(email);
        ResultDto resultDto = (ResultDto) userFound.get("error");
        UserDto userDto = (UserDto) userFound.get("userFound");
        if(resultDto == null){
            return new ResponseEntity(userDto, HttpStatus.OK);
        }
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/byAccount/{accountName}")
    public ResponseEntity findUserByAccountName(@PathVariable String accountName){
        HashMap<String, Object> userFound = service.findUserByAccount(accountName);
        ResultDto resultDto = (ResultDto) userFound.get("error");
        UserDto user = (UserDto) userFound.get("userFound");
        if(resultDto == null){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/byName/{userName}")
    public ResponseEntity findUserByName(@PathVariable String name){
        HashMap<String, Object> userFound = service.findUserByName(name);
        ResultDto resultDto = (ResultDto) userFound.get("error");
        UserDto user = (UserDto) userFound.get("userFound");
        if(resultDto == null){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/listUsers")
    public ResponseEntity getUsers(){
        Optional<List<UserDto>> listUsers = service.getUsers();
        if(!listUsers.isPresent()){
            return new ResponseEntity("No users to show!", HttpStatus.OK);
        }
        return new ResponseEntity(listUsers.get(), HttpStatus.OK);
    }
}
