package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserDao userDao;
    private Connection conn;

    public void updateUsers(String userstr){
        User user =
        try{
            userDao.updateTable(user);
        } catch (SQLException e){
            System.out.println("updateUsers in userservice");
            e.getMessage();
        }
    }
}
