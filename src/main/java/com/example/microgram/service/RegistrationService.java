package com.example.microgram.service;

import com.example.microgram.dao.RegisterUserDao;
import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class RegistrationService {
    private RegisterUserDao regUserDao;

    public ResultDto registerNewUser(RegisterUserDto userDto) {
        String validateErrors = userDto.validateUserData();
        if(!validateErrors.isEmpty()){
            return ResultDto.builder()
                    .message(validateErrors)
                    .build();
        }

        Optional<User> userExists = regUserDao.ifUserExists(userDto);
        if (userExists.isPresent()) {
            return ResultDto.builder()
                    .message("User with this email and account name already exists")
                    .build();
        }

        var newUser = User.builder()
                .accountName(userDto.getAccountName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .build();
        String answer = regUserDao.createNewUser(newUser);

        return ResultDto.builder()
                .message(answer)
                .build();
    }
}
