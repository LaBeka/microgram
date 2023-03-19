package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    private Connection conn;

}
