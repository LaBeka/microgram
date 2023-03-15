package com.example.microgram.dao;

import com.example.microgram.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Post> getPosts(){
        String sql = "select * from posts";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Post.class));
    }
}
