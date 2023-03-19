package com.example.microgram.service;

import com.example.microgram.dao.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;
    private Connection conn;

}
