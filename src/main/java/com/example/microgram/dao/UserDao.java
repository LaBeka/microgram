package com.example.microgram.dao;

import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDao extends BaseDao{
    public UserDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Optional<User> userExistsEmail(String email){
        String sql = "select * from users where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), email)
        ));
    }
    public Optional<User> userExistsAccount(String accountName) {
        String sql = "select * from users where account_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), accountName)
        ));
    }
    public Optional<User> userExistsName(String name) {
        String sql = "select * from users where user_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), name)
        ));
    }
    public Optional<User> userExistsID(Long id) {
        String sql = "select * from users where user_id = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), id)
        ));
    }

    public List<User> getListUsers() {
        String sql = "select * from users;";
        return jdbcTemplate.query(sql, new UserMapper());
    }
    public Long getUsersPostQuantity(User user) throws EmptyResultDataAccessException {
        String sql = "select count(user_id) from posts where user_id = ? group by user_id;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, user.getUserId());
        } catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }

    public Long getUsersFollowerQuantity(User user){
        String sql = "select count(user_being_followed) from follows where user_being_followed = ? group by user_being_followed;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, user.getUserId());
        } catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }
    public Long getUsersFollowingQuantity(User user){
        String sql = "select count(user_following) from follows where user_following = ? group by user_following;";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, user.getUserId());
        } catch (EmptyResultDataAccessException e){
            return 0L;
        }
    }
}
