package com.example.microgram.dao;

import com.example.microgram.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Like> getLikes(){
        String sql = "select * from likes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Like.class));
    }
}
