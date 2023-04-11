package com.example.microgram.mappers;

import com.example.microgram.dto.RegisterUserDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RegisterUserMapper implements RowMapper<RegisterUserDto> {
    @Override
    public RegisterUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegisterUserDto user = new RegisterUserDto();
        user.setUserId(rs.getLong("user_id"));
        user.setAccountName(rs.getString("account_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("user_name"));
        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    }
}
