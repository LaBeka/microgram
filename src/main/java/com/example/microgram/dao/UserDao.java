package com.example.microgram.dao;

import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private Connection conn;

    public boolean updateTable(User user, String sql) {
        jdbcTemplate.update(sql,
                user.getAccountName(),
                user.getEmail(),
                user.getPassword(),
                user.getPostQuantity(),
                user.getFollowQuantity(),
                user.getFollowerQuantity(),
                user.getUserName());
        return true;
    }

    public User findUserById(int entityId, String sql) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entityId);
            if (ps.execute()) {
                return executeStatement(ps);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public User findUserByAcName(String accountName, String sql) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accountName);
            if (ps.execute()) {
                return executeStatement(ps);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private User executeStatement(PreparedStatement ps) throws SQLException{
        ResultSet resultSet = ps.getResultSet();
        resultSet.next();
        int user_id = resultSet.getInt("user_id");
        String accountName = resultSet.getString("account_name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        Integer postQuantity = resultSet.getInt("post_quantity");
        Integer followQuantity = resultSet.getInt("follow_quantity");
        Integer followerQuantity = resultSet.getInt("follower_quantity");
        String username = resultSet.getString("user_name");
        return new User(user_id,
                accountName,
                email,
                password,
                postQuantity,
                followQuantity,
                followerQuantity,
                username);
    }

    public User findUserByName(String userName, String sql) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            if (ps.execute()) {
                return executeStatement(ps);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public User findUserByEmail(String email, String sql) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            if (ps.execute()) {
                return executeStatement(ps);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
