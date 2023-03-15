package com.example.microgram.service;

import com.example.microgram.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    private Connection conn;

//    private void createCommentTable() throws SQLException {
////        String customerTableQuery = "create table comment_microgram(" +
////                "id integer primary key," +
////                "name varchar(40)," +
////                "age integer)";
//        String customerEntryQuery = "insert into customers " +
//                "values(73, 'Brian', 33)";
//        Statement statement = conn.createStatement();
//        int result = statement.executeUpdate(customerEntryQuery);
//    }
}
