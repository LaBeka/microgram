package com.example.microgram.dao;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@Data
@RequiredArgsConstructor
public abstract class BaseDao {
    protected final JdbcTemplate jdbcTemplate;
}

