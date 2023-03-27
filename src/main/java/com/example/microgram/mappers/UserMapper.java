package com.example.microgram.mappers;

import com.example.microgram.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setAccountName(rs.getString("account_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("user_name"));
        user.setRoles(rs.getString("roles"));
        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    }
}
