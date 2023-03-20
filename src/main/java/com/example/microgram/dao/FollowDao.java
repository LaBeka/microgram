package com.example.microgram.dao;

import com.example.microgram.entity.Comment;
import com.example.microgram.entity.Follow;
import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FollowDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Follow> getFollows(){
        String sql = "select * from follows";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Follow.class));
    }

    public Optional getFollowers(User followers){
        String sql = "select * from follows where user_being_followed = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), followers)
        ));
    }

    public Optional getUsersFollowing(User following){
        String sql = "select * from follows where user_following = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comment.class), following)
        ));
    }
}
