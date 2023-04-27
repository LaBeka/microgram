package com.example.microgram.service;

import com.example.microgram.dao.RegisterUserDao;
import com.example.microgram.dto.user.RegUserFrontDto;
import com.example.microgram.dto.user.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService  {
    @Autowired
    private RegisterUserDao regUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<RegUserFrontDto> registerNewUser(RegisterUserDto userDto) {
        var newUser = RegisterUserDto.builder()
                .accountName("Nick name: " + userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .build();
        return regUserDao.createNewUser(newUser);
    }

    public Optional<RegUserFrontDto> checkIfUserExists(RegisterUserDto regUser) {
        return regUserDao.ifIdenticalUserExists(regUser.getEmail());
    }
}
