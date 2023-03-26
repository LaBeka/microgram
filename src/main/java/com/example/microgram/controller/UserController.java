package com.example.microgram.controller;

import com.example.microgram.dto.ResultDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;

    @GetMapping(value = "/find/{email}")
    public ResponseEntity findUserByEmail(@PathVariable String email){
        HashMap<String, Object> userFound = service.findUserByEmail(email);
        ResultDto resultDto = (ResultDto) userFound.get("error");
        UserDto user = (UserDto) userFound.get("userFound");
        if(resultDto == null){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/findByAccount/{accountName}")
    public ResponseEntity findUserByAccountName(@PathVariable String accountName){
        HashMap<String, Object> userFound = service.findUserByAccount(accountName);
        ResultDto resultDto = (ResultDto) userFound.get("error");
        UserDto user = (UserDto) userFound.get("userFound");
        if(resultDto == null){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/findByName/{userName}")
    public ResponseEntity findUserByName(@PathVariable String name){
        HashMap<String, Object> userFound = service.findUserByName(name);
        ResultDto resultDto = (ResultDto) userFound.get("error");
        UserDto user = (UserDto) userFound.get("userFound");
        if(resultDto == null){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(resultDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/deleteUser/{email}")
    public ResponseEntity deleteUserByEmail(@PathVariable String email){
//        ArrayList<String> validateErrors= new ArrayList<>();
//        Optional deleted = service.deleteUserByEmail(email);
//        if(!deleted.isPresent()){
//            validateErrors.add("User by this email is not found");
//            return new ResponseEntity<ResultDto>(new ResultDto(validateErrors), HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }

//    @DeleteMapping(value = "deleteAllUsers")
//    public ResponseEntity deleteAll() throws SQLException {
//        return new ResponseEntity<String>(service.deleteAll(), HttpStatus.OK);
//    }

    @GetMapping(value = "/showUsers")
    public ResponseEntity getUsers(){
        List<UserDto> listUsers = service.getUsers();
        if(listUsers.size() == 0){
            return new ResponseEntity("No users to show!", HttpStatus.OK);
        }
        return new ResponseEntity(listUsers, HttpStatus.OK);
    }
}
