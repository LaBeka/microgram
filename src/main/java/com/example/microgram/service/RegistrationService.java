package com.example.microgram.service;

import com.example.microgram.dao.RegisterUserDao;
import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class RegistrationService {
    private RegisterUserDao regUserDao;

    public boolean findUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        Optional<User> user = regUserDao.ifUserExists(sql, email);
        if(user.isPresent()) {
            return false;
        } else{
            return true;
        }
    }

    public boolean registerUser(RegisterUserDto data){
        String sql = "insert into users (account_name, " +
                "email, " +
                "password, " +
                "post_quantity, " +
                "follow_quantity, " +
                "follower_quantity, " +
                "user_name)\n" +
                "values (?, ?, ?, ?, ?, ?, ?);";
        return regUserDao.registerUser(data, sql);
    }
}
