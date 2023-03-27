package com.example.microgram.controller;

import com.example.microgram.dto.ResultDto;
import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterUserController {
    private RegistrationService service;

    @PostMapping
    public ResultDto register(@RequestBody Authentication data) {
        return service.registerNewUser((RegisterUserDto) data);
    }
}
