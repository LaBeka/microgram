package com.example.microgram.dao;

import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Optional;

@Service
public class RegisterUserDao extends BaseDao {
    public RegisterUserDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Optional<User> ifUserExists(RegisterUserDto userDto){
        String sql = "select * from users where email = ? or account_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), userDto.getEmail(), userDto.getAccountName())
        ));
    }

    public String createNewUser(User newUser) {
        String sql = "insert into users(" +
                "account_name, " +
                "email, " +
                "password, " +
                "user_name, " +
                "roles," +
                "enabled)\n" +
                "values (?, ?, ?, ?, ?, ?);";
        int update = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newUser.getAccountName());
            ps.setString(2, newUser.getEmail());
            ps.setString(3, newUser.getPassword());
            ps.setString(4, newUser.getName());
            ps.setString(5, newUser.getRoles());
            ps.setBoolean(6, newUser.isEnabled());
            return ps;
        });
        if (update == 1){
            return "Success! User is registered!";
        } else {
            return "Register user failed";
        }
    }
}
