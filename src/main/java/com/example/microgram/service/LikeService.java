package com.example.microgram.service;

import com.example.microgram.dao.LikeDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

public class LikeService {
    @Autowired
    private LikeDao likeDao;
    private Connection conn;

}
