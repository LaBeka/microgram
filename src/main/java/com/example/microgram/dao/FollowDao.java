package com.example.microgram.dao;

import com.example.microgram.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Follow> getFollows(){
        String sql = "select * from follows";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Follow.class));
    }
}
