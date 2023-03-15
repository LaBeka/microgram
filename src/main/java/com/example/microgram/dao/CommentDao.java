package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Comment> getComments(){
        String sql = "select * from comments";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class));
    }
}
