package com.example.microgram.dao;

import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public List<User> getUsers(){
        String sql = "select * from users_microgram";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}
