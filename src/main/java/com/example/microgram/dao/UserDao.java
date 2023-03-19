package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public List<User> getUsers(){
        String sql = "select * from users_microgram";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
//
//    private void dropTableIfExists(){
//        String sql = "DROP TABLE IF EXISTS user;";
//        jdbcTemplate.execute(sql);
//    }
//    private void createTable(){
//        String sql = "CREATE TABLE user (\n" +
//                "    user_id serial primary key,\n" +
//                "    account_name varchar(40) unique not null,\n" +
//                "    email varchar(40) not null unique,\n" +
//                "    password varchar(40) not null,\n" +
//                "    post_quantity int,\n" +
//                "    follow_quantity bigint,\n" +
//                "    user_name varchar(40)\n" +
//                "    );";
//        jdbcTemplate.execute(sql);
//    }
    public void updateTable(User user){
        String sql = "insert into users (account_name, email, password, post_quantity, follow_quantity, user_name)\n" +
                "values (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, user.getAccountName(),
                user.getEmail(),
                user.getPassword(),
                user.getPostQuantity(),
                user.getFollowQuantity(),
                user.getUserName());
    }

    public Optional getUserById(int entityId){
        String sql = "select * from users where user_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), entityId)
        ));
    }
    public Optional getUserByAccountName(String accountName){
        String sql = "select * from users where account_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), accountName)
        ));
    }
    public Optional getUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email)
        ));
    }
    public Optional getUserByName(String userName){
        String sql = "select * from users where user_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userName)
        ));
    }
}
