package com.example.microgram.service;

import com.example.microgram.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    private Connection conn;

}
