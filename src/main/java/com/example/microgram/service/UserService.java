package com.example.microgram.service;

import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JdbcTemplate jdbcTemplate;

    public void updateTable(User user) throws SQLException {
        String sql = "insert into users (account_name, email, password, post_quantity, follow_quantity, follower_quantity, user_name)\n" +
                "values (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                user.getAccountName(),
                user.getEmail(),
                user.getPassword(),
                user.getPostQuantity(),
                user.getFollowQuantity(),
                user.getFollowerQuantity(),
                user.getUserName());
    }

    public Optional getUserById(int entityId){
        String sql = "select * from users where user_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), entityId)
        ));
    }

    public Optional getUserByAccountName(String accountName){
        String sql = "select * from users where account_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), accountName)
        ));
    }

    public Optional getUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), email)
        ));
    }

    public Optional getUserByName(String userName){
        String sql = "select * from users where user_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), userName)
        ));
    }

    public Optional deleteUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), email)
        ));
    }
    public String deleteAll() {
//        try{
            String sql = "delete from users;";
//            PreparedStatement ps = jdbcTemplate
//            jdbcTemplate.execute(sql, new PreparedStatement());

            return "All is deleted!";
        /*} catch (SQLException e){
            return e.getMessage();
        }*/
    }

    public List<User> getUsers(){
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new UserMapper());
    }
}
