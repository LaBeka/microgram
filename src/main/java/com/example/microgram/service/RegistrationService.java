package com.example.microgram.service;

import com.example.microgram.dao.RegisterUserDao;
import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.dto.ResultDto;
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

    public ResultDto registerNewUser(RegisterUserDto userDto) {
        String validateErrors = userDto.validateUserData();
        if(!validateErrors.isEmpty()){
            return ResultDto.builder()
                    .message(validateErrors)
                    .build();
        }

        Optional<RegisterUserDto> userExists = regUserDao.ifIdenticalUserExists(userDto);
        if (userExists.isPresent()) {
            return ResultDto.builder()
                    .message("User with this email and account name already exists")
                    .build();
        }

        var newUser = RegisterUserDto.builder()
                .accountName(userDto.getAccountName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .build();
        String answer = regUserDao.createNewUser(newUser);

        return ResultDto.builder()
                .message(answer)
                .build();
    }
}
