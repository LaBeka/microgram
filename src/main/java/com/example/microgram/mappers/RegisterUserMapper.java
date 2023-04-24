package com.example.microgram.mappers;

import com.example.microgram.dto.user.RegUserFrontDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RegisterUserMapper implements RowMapper<RegUserFrontDto> {
    @Override
    public RegUserFrontDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        RegUserFrontDto user = new RegUserFrontDto();
        user.setUserId(rs.getLong("user_id"));
        user.setAccountName(rs.getString("account_name"));
        user.setEmail(rs.getString("email"));
        //user.setPassword(rs.getString("password"));
        user.setName(rs.getString("user_name"));
        return user;
    }
}
