package com.example.microgram.dao;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public abstract class BaseDao {
    protected final JdbcTemplate jdbcTemplate;

    public abstract void createTable();
    public abstract void dropTable();
}

