package com.example.microgram.mappers;

import com.example.microgram.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setAccountName(rs.getString("account_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPostQuantity(rs.getInt("post_quantity"));
        user.setFollowQuantity(rs.getInt("follow_quantity"));
        user.setFollowerQuantity(rs.getInt("follower_quantity"));
        user.setUserName(rs.getString("user_name"));
        return user;
    }
}
